import { Ionicons } from '@expo/vector-icons';
import React from 'react';
import { Pressable, StyleSheet, Text, View } from 'react-native';

import { HealthBadge } from '@/components/HealthBadge';
import { LevelMeter } from '@/components/LevelMeter';
import { PlantAvatar } from '@/components/PlantAvatar';
import { colors, radii, shadow, spacing } from '@/theme/colors';
import { SavedPlant } from '@/types/plant';
import { daysSince, formatRelativeDue } from '@/utils/health';

export function PlantCard({
  plant,
  onPress,
  onLongPress,
}: {
  plant: SavedPlant;
  onPress?: () => void;
  onLongPress?: () => void;
}) {
  const dueInDays = Math.ceil(plant.wateringFrequencyDays - daysSince(plant.lastWateredAt));
  const needsWater = dueInDays <= 0;

  return (
    <Pressable
      onPress={onPress}
      onLongPress={onLongPress}
      style={({ pressed }) => [styles.card, pressed && styles.cardPressed]}
    >
      <PlantAvatar emoji={plant.image} size={52} />
      <View style={styles.body}>
        <Text style={styles.name} numberOfLines={1}>
          {plant.nickname || plant.commonName}
        </Text>
        <Text style={styles.species} numberOfLines={1}>
          {plant.commonName}
        </Text>
        <HealthBadge status={plant.health} compact />
      </View>
      <View style={styles.meta}>
        <View style={styles.wateringRow}>
          <Ionicons
            name="water"
            size={12}
            color={needsWater ? '#D98C1F' : '#5C6B61'}
          />
          <Text style={[styles.watering, needsWater && styles.wateringDue]}>
            {needsWater ? 'Regar hoy' : formatRelativeDue(dueInDays)}
          </Text>
        </View>
        <View style={styles.levels}>
          <LevelMeter icon="water-outline" level={plant.waterLevel} />
          <LevelMeter icon="sunny-outline" level={plant.lightLevel} />
        </View>
      </View>
    </Pressable>
  );
}

const styles = StyleSheet.create({
  card: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: colors.surface,
    borderRadius: radii.lg,
    padding: spacing.md,
    gap: spacing.md,
    ...shadow.card,
  },
  cardPressed: {
    opacity: 0.85,
  },
  body: {
    flex: 1,
    gap: 4,
  },
  name: {
    fontSize: 15.5,
    fontWeight: '700',
    color: colors.text,
  },
  species: {
    fontSize: 12.5,
    color: colors.textMuted,
    fontStyle: 'italic',
    marginBottom: 2,
  },
  meta: {
    alignItems: 'flex-end',
    gap: 6,
  },
  wateringRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 4,
  },
  watering: {
    fontSize: 12,
    color: colors.textMuted,
    fontWeight: '600',
  },
  wateringDue: {
    color: colors.warning,
  },
  levels: {
    gap: 4,
  },
});
