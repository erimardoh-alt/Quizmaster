import React from 'react';
import { StyleSheet, Text, View } from 'react-native';

import { radii, spacing } from '@/theme/colors';
import { HealthStatus } from '@/types/plant';
import { healthMeta } from '@/utils/health';

export function HealthBadge({ status, compact }: { status: HealthStatus; compact?: boolean }) {
  const meta = healthMeta(status);
  return (
    <View style={[styles.badge, { backgroundColor: meta.bg }, compact && styles.badgeCompact]}>
      <Text style={[styles.icon, { color: meta.color }]}>{meta.icon}</Text>
      <Text style={[styles.label, { color: meta.color }]} numberOfLines={1}>
        {meta.label}
      </Text>
    </View>
  );
}

const styles = StyleSheet.create({
  badge: {
    flexDirection: 'row',
    alignItems: 'center',
    alignSelf: 'flex-start',
    paddingHorizontal: spacing.md,
    paddingVertical: spacing.xs,
    borderRadius: radii.pill,
    gap: 6,
  },
  badgeCompact: {
    paddingHorizontal: spacing.sm,
    paddingVertical: 3,
  },
  icon: {
    fontSize: 12,
    fontWeight: '800',
  },
  label: {
    fontSize: 12,
    fontWeight: '700',
  },
});
