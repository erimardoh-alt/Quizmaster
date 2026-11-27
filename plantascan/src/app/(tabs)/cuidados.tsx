import { Ionicons } from '@expo/vector-icons';
import { Link } from 'expo-router';
import React from 'react';
import { ScrollView, StyleSheet, Text, View } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';

import { HealthBadge } from '@/components/HealthBadge';
import { PlantAvatar } from '@/components/PlantAvatar';
import { StatCard } from '@/components/StatCard';
import { TipCard } from '@/components/TipCard';
import { useGarden } from '@/context/GardenContext';
import { QUICK_TIPS } from '@/data/mockPlants';
import { colors, radii, shadow, spacing } from '@/theme/colors';
import { formatRelativeDue } from '@/utils/health';

export default function CuidadosScreen() {
  const { stats } = useGarden();
  const featured = stats.withIssues[0];

  return (
    <SafeAreaView style={styles.safeArea} edges={['top']}>
      <ScrollView contentContainerStyle={styles.scrollContent}>
        <View style={styles.header}>
          <Text style={styles.title}>Cuidados</Text>
          <Text style={styles.subtitle}>Panel de seguimiento de tu jardín.</Text>
        </View>

        <View style={styles.statsGrid}>
          <StatCard icon="leaf" value={stats.total} label="Plantas guardadas" />
          <StatCard
            icon="water"
            value={stats.needsWaterToday.length}
            label="Necesitan riego hoy"
            tint={stats.needsWaterToday.length > 0 ? colors.infoBg : undefined}
            iconColor={stats.needsWaterToday.length > 0 ? colors.info : undefined}
          />
          <StatCard
            icon="alert-circle"
            value={stats.withIssues.length}
            label="Con posible problema"
            tint={stats.withIssues.length > 0 ? colors.dangerBg : undefined}
            iconColor={stats.withIssues.length > 0 ? colors.danger : undefined}
          />
          <StatCard
            icon="notifications"
            value={stats.upcoming.length}
            label="Próximos recordatorios"
            tint={colors.accentBg}
            iconColor={colors.accent}
          />
        </View>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Diagnóstico destacado</Text>
          {featured ? (
            <View style={styles.featuredCard}>
              <View style={styles.featuredHeader}>
                <PlantAvatar emoji={featured.image} size={48} />
                <View style={styles.featuredHeaderText}>
                  <Text style={styles.featuredName} numberOfLines={1}>
                    {featured.nickname || featured.commonName}
                  </Text>
                  <HealthBadge status={featured.health} compact />
                </View>
              </View>
              <Text style={styles.featuredTip}>{featured.careTip}</Text>
              <Link href="/jardin" style={styles.featuredLink}>
                Ver en Mi Jardín →
              </Link>
            </View>
          ) : (
            <View style={styles.featuredCard}>
              <View style={styles.featuredHeader}>
                <View style={styles.okIconWrap}>
                  <Ionicons name="checkmark-circle" size={28} color={colors.success} />
                </View>
                <View style={styles.featuredHeaderText}>
                  <Text style={styles.featuredName}>Todo tu jardín luce saludable</Text>
                  <Text style={styles.featuredTipMuted}>Sigue así, no hay problemas detectados.</Text>
                </View>
              </View>
            </View>
          )}
        </View>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Próximos recordatorios</Text>
          {stats.upcoming.length === 0 ? (
            <Text style={styles.emptyText}>No tienes recordatorios pendientes.</Text>
          ) : (
            <View style={styles.reminderList}>
              {stats.upcoming.map(({ plant, dueInDays }) => (
                <View key={plant.id} style={styles.reminderRow}>
                  <PlantAvatar emoji={plant.image} size={36} />
                  <Text style={styles.reminderName} numberOfLines={1}>
                    {plant.nickname || plant.commonName}
                  </Text>
                  <Text
                    style={[styles.reminderDue, dueInDays <= 0 && styles.reminderDueNow]}
                  >
                    {formatRelativeDue(dueInDays)}
                  </Text>
                </View>
              ))}
            </View>
          )}
        </View>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Consejos rápidos</Text>
          <View style={styles.tipsList}>
            {QUICK_TIPS.map((tip) => (
              <TipCard key={tip.id} tip={tip} />
            ))}
          </View>
        </View>
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
    gap: spacing.xl,
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
  },
  statsGrid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: spacing.md,
  },
  section: {
    gap: spacing.md,
  },
  sectionTitle: {
    fontSize: 17,
    fontWeight: '800',
    color: colors.text,
  },
  featuredCard: {
    backgroundColor: colors.surface,
    borderRadius: radii.lg,
    padding: spacing.lg,
    gap: spacing.sm,
    borderWidth: 1.5,
    borderColor: colors.accent,
    ...shadow.card,
  },
  featuredHeader: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: spacing.md,
  },
  featuredHeaderText: {
    flex: 1,
    gap: 4,
  },
  featuredName: {
    fontSize: 15.5,
    fontWeight: '700',
    color: colors.text,
  },
  featuredTip: {
    fontSize: 13.5,
    color: colors.textMuted,
    lineHeight: 19,
  },
  featuredTipMuted: {
    fontSize: 12.5,
    color: colors.textMuted,
  },
  featuredLink: {
    fontSize: 13,
    fontWeight: '700',
    color: colors.primary,
    marginTop: 2,
  },
  okIconWrap: {
    width: 48,
    height: 48,
    borderRadius: radii.lg,
    backgroundColor: colors.successBg,
    alignItems: 'center',
    justifyContent: 'center',
  },
  emptyText: {
    fontSize: 13,
    color: colors.textMuted,
  },
  reminderList: {
    backgroundColor: colors.surface,
    borderRadius: radii.lg,
    padding: spacing.sm,
    gap: 2,
    ...shadow.card,
  },
  reminderRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: spacing.md,
    paddingVertical: spacing.sm,
    paddingHorizontal: spacing.sm,
  },
  reminderName: {
    flex: 1,
    fontSize: 14,
    fontWeight: '600',
    color: colors.text,
  },
  reminderDue: {
    fontSize: 12.5,
    fontWeight: '700',
    color: colors.textMuted,
  },
  reminderDueNow: {
    color: colors.warning,
  },
  tipsList: {
    gap: spacing.sm,
  },
});
