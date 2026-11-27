import { Ionicons } from '@expo/vector-icons';
import { Tabs } from 'expo-router/tabs';
import React from 'react';

import { colors } from '@/theme/colors';

export default function TabsLayout() {
  return (
    <Tabs
      screenOptions={{
        headerShown: false,
        tabBarActiveTintColor: colors.primary,
        tabBarInactiveTintColor: colors.textFaint,
        tabBarStyle: {
          backgroundColor: colors.surface,
          borderTopColor: colors.border,
          height: 64,
          paddingTop: 8,
          paddingBottom: 10,
        },
        tabBarLabelStyle: {
          fontSize: 11.5,
          fontWeight: '700',
        },
      }}
    >
      <Tabs.Screen
        name="index"
        options={{
          title: 'Escanear',
          tabBarIcon: ({ color, size, focused }) => (
            <Ionicons name={focused ? 'camera' : 'camera-outline'} size={size} color={(color as string) ?? colors.textFaint} />
          ),
        }}
      />
      <Tabs.Screen
        name="jardin"
        options={{
          title: 'Mi Jardín',
          tabBarIcon: ({ color, size, focused }) => (
            <Ionicons name={focused ? 'leaf' : 'leaf-outline'} size={size} color={(color as string) ?? colors.textFaint} />
          ),
        }}
      />
      <Tabs.Screen
        name="cuidados"
        options={{
          title: 'Cuidados',
          tabBarIcon: ({ color, size, focused }) => (
            <Ionicons name={focused ? 'pulse' : 'pulse-outline'} size={size} color={(color as string) ?? colors.textFaint} />
          ),
        }}
      />
    </Tabs>
  );
}
