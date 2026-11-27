import { colors } from '@/theme/colors';
import { HealthStatus, LightLevel, WaterLevel } from '@/types/plant';

export function healthMeta(status: HealthStatus) {
  switch (status) {
    case 'saludable':
      return { label: 'Saludable', color: colors.success, bg: colors.successBg, icon: '✓' };
    case 'atencion':
      return { label: 'Necesita atención', color: colors.warning, bg: colors.warningBg, icon: '!' };
    case 'critica':
      return { label: 'Estado crítico', color: colors.danger, bg: colors.dangerBg, icon: '✕' };
  }
}

export function levelMeta(level: WaterLevel | LightLevel) {
  switch (level) {
    case 'bajo':
      return { label: 'Bajo', value: 1 };
    case 'medio':
      return { label: 'Medio', value: 2 };
    case 'alto':
      return { label: 'Alto', value: 3 };
  }
}

export function daysSince(timestamp: number): number {
  return (Date.now() - timestamp) / (24 * 60 * 60 * 1000);
}

export function formatRelativeDue(dueInDays: number): string {
  const rounded = Math.round(dueInDays);
  if (rounded <= 0) return rounded === 0 ? 'Hoy' : `Atrasado ${Math.abs(rounded)}d`;
  if (rounded === 1) return 'Mañana';
  return `En ${rounded} días`;
}
