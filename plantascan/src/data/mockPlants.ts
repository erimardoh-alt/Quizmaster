import { QuickTip, ScanResult } from '@/types/plant';

export const PLANT_DATABASE: ScanResult[] = [
  {
    id: 'monstera',
    commonName: 'Monstera Deliciosa',
    scientificName: 'Monstera deliciosa',
    health: 'saludable',
    confidence: 96,
    careTip: 'Riega cuando los primeros 3 cm de tierra estén secos. Prefiere luz indirecta brillante.',
    image: '🌿',
    waterLevel: 'medio',
    lightLevel: 'medio',
    wateringFrequencyDays: 7,
  },
  {
    id: 'pothos',
    commonName: 'Potos',
    scientificName: 'Epipremnum aureum',
    health: 'saludable',
    confidence: 94,
    careTip: 'Muy resistente. Tolera poca luz y riegos espaciados; deja secar la tierra entre riegos.',
    image: '🪴',
    waterLevel: 'bajo',
    lightLevel: 'bajo',
    wateringFrequencyDays: 9,
  },
  {
    id: 'sanseviera',
    commonName: 'Sansevieria',
    scientificName: 'Dracaena trifasciata',
    health: 'saludable',
    confidence: 98,
    careTip: 'Ideal para principiantes. Riego escaso: cada 2-3 semanas es suficiente.',
    image: '🌵',
    waterLevel: 'bajo',
    lightLevel: 'bajo',
    wateringFrequencyDays: 14,
  },
  {
    id: 'ficus',
    commonName: 'Ficus Lira',
    scientificName: 'Ficus lyrata',
    health: 'atencion',
    confidence: 87,
    careTip: 'Hojas con bordes secos: revisa el riego y evita corrientes de aire frío.',
    image: '🌳',
    waterLevel: 'medio',
    lightLevel: 'alto',
    wateringFrequencyDays: 6,
    },
  {
    id: 'suculenta',
    commonName: 'Suculenta Echeveria',
    scientificName: 'Echeveria elegans',
    health: 'saludable',
    confidence: 92,
    careTip: 'Necesita mucha luz directa y riego muy espaciado para evitar pudrición de raíz.',
    image: '🌱',
    waterLevel: 'bajo',
    lightLevel: 'alto',
    wateringFrequencyDays: 12,
  },
  {
    id: 'orquidea',
    commonName: 'Orquídea Phalaenopsis',
    scientificName: 'Phalaenopsis amabilis',
    health: 'atencion',
    confidence: 83,
    careTip: 'Raíces algo blandas detectadas. Reduce el riego y asegura buen drenaje.',
    image: '🌸',
    waterLevel: 'medio',
    lightLevel: 'medio',
    wateringFrequencyDays: 8,
  },
  {
    id: 'helecho',
    commonName: 'Helecho de Boston',
    scientificName: 'Nephrolepis exaltata',
    health: 'critica',
    confidence: 78,
    careTip: 'Hojas amarillentas por falta de humedad. Aumenta la frecuencia de riego y nebuliza las hojas.',
    image: '🍃',
    waterLevel: 'alto',
    lightLevel: 'medio',
    wateringFrequencyDays: 4,
  },
  {
    id: 'cactus',
    commonName: 'Cactus Saguaro',
    scientificName: 'Carnegiea gigantea',
    health: 'saludable',
    confidence: 95,
    careTip: 'Riego mínimo. Permite que la tierra se seque por completo entre riegos.',
    image: '🌵',
    waterLevel: 'bajo',
    lightLevel: 'alto',
    wateringFrequencyDays: 18,
  },
];

export function getRandomScanResult(): ScanResult {
  const base = PLANT_DATABASE[Math.floor(Math.random() * PLANT_DATABASE.length)];
  const jitter = Math.floor(Math.random() * 5) - 2;
  const confidence = Math.min(99, Math.max(70, base.confidence + jitter));
  return { ...base, confidence };
}

export const QUICK_TIPS: QuickTip[] = [
  {
    id: 'tip-luz',
    title: 'Rota tus plantas',
    description: 'Gira las macetas un cuarto de vuelta cada semana para un crecimiento parejo.',
    icon: 'sync-outline',
  },
  {
    id: 'tip-riego',
    title: 'Riega por la mañana',
    description: 'Reduce la evaporación y ayuda a que la planta absorba mejor el agua durante el día.',
    icon: 'water-outline',
  },
  {
    id: 'tip-humedad',
    title: 'Agrupa tus plantas',
    description: 'Colocarlas cerca aumenta la humedad ambiental y reduce el estrés hídrico.',
    icon: 'partly-sunny-outline',
  },
  {
    id: 'tip-polvo',
    title: 'Limpia las hojas',
    description: 'Hojas sin polvo absorben más luz y respiran mejor.',
    icon: 'sparkles-outline',
  },
];
