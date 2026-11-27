import { Ionicons } from '@expo/vector-icons';
import React from 'react';
import { Alert, FlatList, StyleSheet, Text, View } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';

import { PlantCard } from '@/components/PlantCard';
import { useGarden } from '@/context/GardenContext';
import { colors, spacing } from '@/theme/colors';
import { SavedPlant } from '@/types/plant';

export default function JardinScreen() {
  const { plants, waterPlant, removePlant } = useGarden();

  const handlePress = (plant: SavedPlant) => {
    Alert.alert(
      plant.nickname || plant.commonName,
      '¿Qué deseas hacer?',
      [
        { text: 'Marcar como regada', onPress: () => waterPlant(plant.id) },
        { text: 'Eliminar de Mi Jardín', style: 'destructive', onPress: () => removePlant(plant.id) },
        { text: 'Cancelar', style: 'cancel' },
      ],
      { cancelable: true }
    );
  };

  return (
    <SafeAreaView style={styles.safeArea} edges={['top']}>
      <FlatList
        data={plants}
        keyExtractor={(item) => item.id}
        contentContainerStyle={styles.listContent}
        ListHeaderComponent={
          <View style={styles.header}>
            <Text style={styles.title}>Mi Jardín</Text>
            <Text style={styles.subtitle}>
              {plants.length === 0
                ? 'Aún no has guardado plantas.'
                : `${plants.length} planta${plants.length === 1 ? '' : 's'} en tu colección`}
            </Text>
          </View>
        }
        ItemSeparatorComponent={() => <View style={{ height: spacing.md }} />}
        renderItem={({ item }) => <PlantCard plant={item} onPress={() => handlePress(item)} />}
        ListEmptyComponent={
          <View style={styles.empty}>
            <Ionicons name="leaf-outline" size={44} color={colors.textFaint} />
            <Text style={styles.emptyTitle}>Tu jardín está vacío</Text>
            <Text style={styles.emptyText}>
              Ve a la pestaña Escanear, identifica una planta y guárdala aquí.
            </Text>
          </View>
        }
      />
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: colors.background,
  },
  listContent: {
    padding: spacing.lg,
    paddingBottom: spacing.xxl * 2,
    flexGrow: 1,
  },
  header: {
    gap: 4,
    marginBottom: spacing.lg,
  },
  title: {
    fontSize: 28,
    fontWeight: '800',
    color: colors.text,
  },
  subtitle: {
    fontSize: 14,
    color: colors.textMuted,
  },
  empty: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    gap: spacing.sm,
    paddingTop: spacing.xxl * 2,
  },
  emptyTitle: {
    fontSize: 16,
    fontWeight: '700',
    color: colors.text,
  },
  emptyText: {
    fontSize: 13,
    color: colors.textMuted,
    textAlign: 'center',
    maxWidth: 260,
  },
});
