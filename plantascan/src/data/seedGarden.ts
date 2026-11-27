import { SavedPlant } from '@/types/plant';
import { PLANT_DATABASE } from './mockPlants';

const DAY = 24 * 60 * 60 * 1000;

function daysAgo(days: number): number {
  return Date.now() - days * DAY;
}

function findPlant(id: string) {
  const plant = PLANT_DATABASE.find((p) => p.id === id);
  if (!plant) throw new Error(`Unknown seed plant: ${id}`);
  return plant;
}

export function buildSeedGarden(): SavedPlant[] {
  return [
    {
      ...findPlant('monstera'),
      nickname: 'Monstera de la sala',
      savedAt: daysAgo(18),
      lastWateredAt: daysAgo(8),
    },
    {
      ...findPlant('sanseviera'),
      nickname: 'Sansevieria del pasillo',
      savedAt: daysAgo(40),
      lastWateredAt: daysAgo(5),
    },
    {
      ...findPlant('helecho'),
      nickname: 'Helecho del baño',
      savedAt: daysAgo(10),
      lastWateredAt: daysAgo(6),
    },
    {
      ...findPlant('orquidea'),
      nickname: 'Orquídea de la cocina',
      savedAt: daysAgo(25),
      lastWateredAt: daysAgo(3),
    },
  ];
}
