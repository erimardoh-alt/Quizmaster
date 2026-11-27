import { Ionicons } from '@expo/vector-icons';
import * as ImagePicker from 'expo-image-picker';
import React, { useState } from 'react';
import {
  ActivityIndicator,
  Alert,
  Image,
  Platform,
  Pressable,
  ScrollView,
  StyleSheet,
  Text,
  View,
} from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';

import { ResultCard } from '@/components/ResultCard';
import { useGarden } from '@/context/GardenContext';
import { plantIdentificationService } from '@/services/plantIdentificationService';
import { colors, radii, shadow, spacing } from '@/theme/colors';
import { ScanResult } from '@/types/plant';

type ScanState = 'idle' | 'analyzing' | 'done';

export default function EscanearScreen() {
  const { addPlant } = useGarden();
  const [imageUri, setImageUri] = useState<string | null>(null);
  const [state, setState] = useState<ScanState>('idle');
  const [result, setResult] = useState<ScanResult | null>(null);
  const [saved, setSaved] = useState(false);

  const runScan = async (uri: string) => {
    setImageUri(uri);
    setResult(null);
    setSaved(false);
    setState('analyzing');
    try {
      const identified = await plantIdentificationService.identify(uri);
      setResult(identified);
      setState('done');
    } catch {
      Alert.alert('No se pudo analizar la imagen', 'Intenta escanear de nuevo.');
      setState('idle');
    }
  };

  const handlePick = async (source: 'camera' | 'library') => {
    try {
      const permission =
        source === 'camera'
          ? await ImagePicker.requestCameraPermissionsAsync()
          : await ImagePicker.requestMediaLibraryPermissionsAsync();

      if (!permission.granted) {
        Alert.alert(
          'Permiso necesario',
          source === 'camera'
            ? 'Activa el permiso de cámara para capturar una foto.'
            : 'Activa el permiso de galería para subir una imagen.'
        );
        return;
      }

      const pickerResult =
        source === 'camera'
          ? await ImagePicker.launchCameraAsync({ quality: 0.7, allowsEditing: true })
          : await ImagePicker.launchImageLibraryAsync({ quality: 0.7, allowsEditing: true });

      if (!pickerResult.canceled && pickerResult.assets?.[0]?.uri) {
        await runScan(pickerResult.assets[0].uri);
      }
    } catch {
      Alert.alert('No se pudo abrir la cámara', 'Intenta subir una imagen desde la galería.');
    }
  };

  const handleReset = () => {
    setImageUri(null);
    setResult(null);
    setState('idle');
    setSaved(false);
  };

  const handleSave = () => {
    if (!result) return;
    addPlant(result);
    setSaved(true);
  };

  return (
    <SafeAreaView style={styles.safeArea} edges={['top']}>
      <ScrollView contentContainerStyle={styles.scrollContent}>
        <View style={styles.header}>
          <Text style={styles.title}>Escanear</Text>
          <Text style={styles.subtitle}>
            Enfoca una planta y PlantaScan identificará su especie y estado de salud.
          </Text>
        </View>

        <View style={styles.viewfinder}>
          {imageUri ? (
            <Image source={{ uri: imageUri }} style={styles.previewImage} resizeMode="cover" />
          ) : (
            <View style={styles.viewfinderPlaceholder}>
              <Ionicons name="leaf-outline" size={56} color="rgba(255,255,255,0.85)" />
              <Text style={styles.viewfinderHint}>Encuadra la planta aquí</Text>
            </View>
          )}

          <View pointerEvents="none" style={styles.focusFrame}>
            <View style={[styles.corner, styles.cornerTL]} />
            <View style={[styles.corner, styles.cornerTR]} />
            <View style={[styles.corner, styles.cornerBL]} />
            <View style={[styles.corner, styles.cornerBR]} />
          </View>

          {state === 'analyzing' && (
            <View style={styles.analyzingOverlay}>
              <ActivityIndicator color={colors.white} size="large" />
              <Text style={styles.analyzingText}>Analizando planta…</Text>
            </View>
          )}
        </View>

        {state !== 'analyzing' && (
          <View style={styles.actions}>
            <Pressable
              onPress={() => handlePick('camera')}
              style={({ pressed }) => [styles.shutterButton, pressed && styles.shutterPressed]}
            >
              <View style={styles.shutterInner}>
                <Ionicons name="camera" size={26} color={colors.white} />
              </View>
            </Pressable>
            <Text style={styles.shutterLabel}>Capturar</Text>
            <Pressable
              onPress={() => handlePick('library')}
              style={({ pressed }) => [styles.uploadButton, pressed && styles.uploadPressed]}
            >
              <Ionicons name="cloud-upload-outline" size={16} color={colors.primaryDark} />
              <Text style={styles.uploadButtonText}>Subir imagen</Text>
            </Pressable>
          </View>
        )}

        {Platform.OS === 'web' && state === 'idle' && (
          <Text style={styles.webHint}>
            En el navegador, usa &quot;Subir imagen&quot; si tu cámara no está disponible.
          </Text>
        )}

        {state === 'done' && result && (
          <View style={styles.resultWrap}>
            <ResultCard result={result} onSave={handleSave} saved={saved} />
            <Pressable onPress={handleReset} style={styles.scanAgain}>
              <Text style={styles.scanAgainText}>Escanear otra planta</Text>
            </Pressable>
          </View>
        )}
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: colors.background,
  },
  scrollContent: {
    padding: spacing.lg,
    paddingBottom: spacing.xxl * 2,
    gap: spacing.lg,
  },
  header: {
    gap: 4,
  },
  title: {
    fontSize: 28,
    fontWeight: '800',
    color: colors.text,
  },
  subtitle: {
    fontSize: 14,
    color: colors.textMuted,
    lineHeight: 20,
  },
  viewfinder: {
    aspectRatio: 1,
    borderRadius: radii.xl,
    backgroundColor: colors.primaryDark,
    overflow: 'hidden',
    justifyContent: 'center',
    alignItems: 'center',
    ...shadow.card,
  },
  previewImage: {
    width: '100%',
    height: '100%',
  },
  viewfinderPlaceholder: {
    alignItems: 'center',
    gap: spacing.sm,
  },
  viewfinderHint: {
    color: 'rgba(255,255,255,0.75)',
    fontSize: 13,
    fontWeight: '600',
  },
  focusFrame: {
    ...StyleSheet.absoluteFill,
    margin: spacing.xl,
  },
  corner: {
    position: 'absolute',
    width: 28,
    height: 28,
    borderColor: 'rgba(255,255,255,0.85)',
  },
  cornerTL: { top: 0, left: 0, borderTopWidth: 3, borderLeftWidth: 3, borderTopLeftRadius: 10 },
  cornerTR: { top: 0, right: 0, borderTopWidth: 3, borderRightWidth: 3, borderTopRightRadius: 10 },
  cornerBL: { bottom: 0, left: 0, borderBottomWidth: 3, borderLeftWidth: 3, borderBottomLeftRadius: 10 },
  cornerBR: { bottom: 0, right: 0, borderBottomWidth: 3, borderRightWidth: 3, borderBottomRightRadius: 10 },
  analyzingOverlay: {
    ...StyleSheet.absoluteFill,
    backgroundColor: colors.overlay,
    alignItems: 'center',
    justifyContent: 'center',
    gap: spacing.sm,
  },
  analyzingText: {
    color: colors.white,
    fontWeight: '700',
    fontSize: 14,
  },
  actions: {
    alignItems: 'center',
    gap: spacing.md,
  },
  shutterButton: {
    width: 78,
    height: 78,
    borderRadius: 39,
    backgroundColor: colors.surface,
    borderWidth: 4,
    borderColor: colors.primary,
    alignItems: 'center',
    justifyContent: 'center',
    ...shadow.floating,
  },
  shutterPressed: {
    opacity: 0.8,
  },
  shutterInner: {
    width: 58,
    height: 58,
    borderRadius: 29,
    backgroundColor: colors.primary,
    alignItems: 'center',
    justifyContent: 'center',
  },
  shutterLabel: {
    marginTop: -spacing.sm,
    fontSize: 12,
    fontWeight: '600',
    color: colors.textMuted,
  },
  uploadButton: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 6,
    paddingHorizontal: spacing.lg,
    paddingVertical: spacing.sm,
    borderRadius: radii.pill,
    backgroundColor: colors.surfaceMuted,
  },
  uploadPressed: {
    opacity: 0.7,
  },
  uploadButtonText: {
    fontSize: 13.5,
    fontWeight: '700',
    color: colors.primaryDark,
  },
  webHint: {
    textAlign: 'center',
    fontSize: 12,
    color: colors.textFaint,
  },
  resultWrap: {
    gap: spacing.md,
  },
  scanAgain: {
    alignItems: 'center',
    paddingVertical: spacing.sm,
  },
  scanAgainText: {
    color: colors.primary,
    fontWeight: '700',
    fontSize: 13.5,
  },
});
