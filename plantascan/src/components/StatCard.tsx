import { Ionicons } from '@expo/vector-icons';
import React from 'react';
import { StyleSheet, Text, View } from 'react-native';

import { colors, radii, shadow, spacing } from '@/theme/colors';

export function StatCard({
  icon,
  value,
  label,
  tint,
  iconColor,
}: {
  icon: keyof typeof Ionicons.glyphMap;
  value: string | number;
  label: string;
  tint?: string;
  iconColor?: string;
}) {
  return (
    <View style={styles.card}>
      <View style={[styles.iconWrap, tint ? { backgroundColor: tint } : null]}>
        <Ionicons name={icon} size={18} color={iconColor ?? colors.primary} />
      </View>
      <Text style={styles.value}>{value}</Text>
      <Text style={styles.label} numberOfLines={2}>
        {label}
      </Text>
    </View>
  );
}

const styles = StyleSheet.create({
  card: {
    flexBasis: '47%',
    flexGrow: 1,
    backgroundColor: colors.surface,
    borderRadius: radii.lg,
    padding: spacing.lg,
    ...shadow.card,
  },
  iconWrap: {
    width: 36,
    height: 36,
    borderRadius: radii.md,
    backgroundColor: colors.primaryLight,
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: spacing.sm,
  },
  value: {
    fontSize: 24,
    fontWeight: '800',
    color: colors.text,
  },
  label: {
    fontSize: 12.5,
    color: colors.textMuted,
    marginTop: 2,
  },
});
