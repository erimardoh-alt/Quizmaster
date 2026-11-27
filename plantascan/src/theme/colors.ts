export const colors = {
  background: '#EFF6EF',
  surface: '#FFFFFF',
  surfaceMuted: '#E7F2E8',

  primary: '#2F6E4E',
  primaryDark: '#1F4D36',
  primaryLight: '#DEEEE1',
  accent: '#F4B942',
  accentBg: '#FCF1D8',

  text: '#1D2A22',
  textMuted: '#5C6B61',
  textFaint: '#8C978E',
  border: '#E1E8DF',

  success: '#2F9E5B',
  successBg: '#E5F6EB',
  warning: '#D98C1F',
  warningBg: '#FBF0DC',
  danger: '#D1483F',
  dangerBg: '#FBE6E4',
  info: '#2E7BB8',
  infoBg: '#E4F1FA',

  white: '#FFFFFF',
  black: '#000000',
  overlay: 'rgba(16, 32, 22, 0.55)',
} as const;

export const radii = {
  sm: 10,
  md: 16,
  lg: 24,
  xl: 32,
  pill: 999,
};

export const spacing = {
  xs: 4,
  sm: 8,
  md: 12,
  lg: 16,
  xl: 24,
  xxl: 32,
};

export const shadow = {
  card: {
    shadowColor: '#0F2318',
    shadowOffset: { width: 0, height: 6 },
    shadowOpacity: 0.08,
    shadowRadius: 12,
    elevation: 3,
  },
  floating: {
    shadowColor: '#0F2318',
    shadowOffset: { width: 0, height: 10 },
    shadowOpacity: 0.18,
    shadowRadius: 20,
    elevation: 8,
  },
};
