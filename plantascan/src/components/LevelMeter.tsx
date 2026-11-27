import { Ionicons } from '@expo/vector-icons';
import React from 'react';
import { StyleSheet, View } from 'react-native';

import { colors, spacing } from '@/theme/colors';
import { LightLevel, WaterLevel } from '@/types/plant';
import { levelMeta } from '@/utils/health';

export function LevelMeter({
  icon,
  level,
}: {
  icon: keyof typeof Ionicons.glyphMap;
  level: WaterLevel | LightLevel;
}) {
  const meta = levelMeta(level);
  return (
    <View style={styles.row}>
      <Ionicons name={icon} size={13} color={colors.textMuted} />
      <View style={styles.dots}>
        {[1, 2, 3].map((dot) => (
          <View
            key={dot}
            style={[
              styles.dot,
              { backgroundColor: dot <= meta.value ? colors.primary : colors.border },
            ]}
          />
        ))}
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  row: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: spacing.xs,
  },
  dots: {
    flexDirection: 'row',
    gap: 3,
  },
  dot: {
    width: 6,
    height: 6,
    borderRadius: 3,
  },
});
