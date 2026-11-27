import { Ionicons } from '@expo/vector-icons';
import React from 'react';
import { Pressable, StyleSheet, Text, View } from 'react-native';

import { HealthBadge } from '@/components/HealthBadge';
import { PlantAvatar } from '@/components/PlantAvatar';
import { colors, radii, shadow, spacing } from '@/theme/colors';
import { ScanResult } from '@/types/plant';

export function ResultCard({
  result,
  onSave,
  saved,
}: {
  result: ScanResult;
  onSave: () => void;
  saved: boolean;
}) {
  return (
    <View style={styles.card}>
      <View style={styles.header}>
        <PlantAvatar emoji={result.image} size={64} />
        <View style={styles.headerText}>
          <Text style={styles.commonName}>{result.commonName}</Text>
          <Text style={styles.scientificName}>{result.scientificName}</Text>
        </View>
      </View>

      <View style={styles.row}>
        <HealthBadge status={result.health} />
        <View style={styles.confidence}>
          <Text style={styles.confidenceValue}>{result.confidence}%</Text>
          <Text style={styles.confidenceLabel}>confianza</Text>
        </View>
      </View>

      <View style={styles.tipBox}>
        <Text style={styles.tipLabel}>Recomendación</Text>
        <Text style={styles.tipText}>{result.careTip}</Text>
      </View>

      <Pressable
        onPress={onSave}
        disabled={saved}
        style={({ pressed }) => [
          styles.saveButton,
          saved && styles.saveButtonDone,
          pressed && !saved && styles.saveButtonPressed,
        ]}
      >
        <View style={styles.saveButtonContent}>
          {saved && <Ionicons name="checkmark-circle" size={17} color={colors.success} />}
          <Text style={[styles.saveButtonText, saved && styles.saveButtonTextDone]}>
            {saved ? 'Guardada en Mi Jardín' : 'Guardar en Mi Jardín'}
          </Text>
        </View>
      </Pressable>
    </View>
  );
}

const styles = StyleSheet.create({
  card: {
    backgroundColor: colors.surface,
    borderRadius: radii.xl,
    padding: spacing.lg,
    gap: spacing.md,
    ...shadow.floating,
  },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: spacing.md,
  },
  headerText: {
    flex: 1,
  },
  commonName: {
    fontSize: 19,
    fontWeight: '800',
    color: colors.text,
  },
  scientificName: {
    fontSize: 13.5,
    color: colors.textMuted,
    fontStyle: 'italic',
    marginTop: 2,
  },
  row: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
  },
  confidence: {
    alignItems: 'flex-end',
  },
  confidenceValue: {
    fontSize: 16,
    fontWeight: '800',
    color: colors.primary,
  },
  confidenceLabel: {
    fontSize: 11,
    color: colors.textFaint,
  },
  tipBox: {
    backgroundColor: colors.surfaceMuted,
    borderRadius: radii.md,
    padding: spacing.md,
    gap: 4,
  },
  tipLabel: {
    fontSize: 11.5,
    fontWeight: '700',
    color: colors.textMuted,
    textTransform: 'uppercase',
    letterSpacing: 0.4,
  },
  tipText: {
    fontSize: 13.5,
    color: colors.text,
    lineHeight: 19,
  },
  saveButton: {
    backgroundColor: colors.primary,
    borderRadius: radii.pill,
    paddingVertical: spacing.md,
    alignItems: 'center',
  },
  saveButtonPressed: {
    backgroundColor: colors.primaryDark,
  },
  saveButtonDone: {
    backgroundColor: colors.successBg,
  },
  saveButtonContent: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 6,
  },
  saveButtonText: {
    color: colors.white,
    fontWeight: '700',
    fontSize: 15,
  },
  saveButtonTextDone: {
    color: colors.success,
  },
});
