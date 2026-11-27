export type HealthStatus = 'saludable' | 'atencion' | 'critica';

export type WaterLevel = 'bajo' | 'medio' | 'alto';
export type LightLevel = 'bajo' | 'medio' | 'alto';

export interface ScanResult {
  id: string;
  commonName: string;
  scientificName: string;
  health: HealthStatus;
  confidence: number;
  careTip: string;
  image: string;
  waterLevel: WaterLevel;
  lightLevel: LightLevel;
  wateringFrequencyDays: number;
}

export interface SavedPlant extends ScanResult {
  savedAt: number;
  lastWateredAt: number;
  nickname?: string;
}

export interface QuickTip {
  id: string;
  title: string;
  description: string;
  icon: string;
}
