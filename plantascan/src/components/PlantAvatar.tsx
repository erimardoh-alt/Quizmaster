import React from 'react';
import { StyleSheet, Text, View, ViewStyle } from 'react-native';

import { colors, radii } from '@/theme/colors';

export function PlantAvatar({
  emoji,
  size = 56,
  style,
}: {
  emoji: string;
  size?: number;
  style?: ViewStyle;
}) {
  return (
    <View
      style={[
        styles.container,
        { width: size, height: size, borderRadius: radii.lg },
        style,
      ]}
    >
      <Text style={{ fontSize: size * 0.5 }}>{emoji}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: colors.primaryLight,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
