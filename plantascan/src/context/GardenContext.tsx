import AsyncStorage from '@react-native-async-storage/async-storage';
import React, { createContext, useContext, useEffect, useMemo, useState } from 'react';

import { buildSeedGarden } from '@/data/seedGarden';
import { ScanResult, SavedPlant } from '@/types/plant';

const STORAGE_KEY = 'plantascan:garden:v1';
const DAY = 24 * 60 * 60 * 1000;

interface GardenStats {
  total: number;
  needsWaterToday: SavedPlant[];
  withIssues: SavedPlant[];
  upcoming: { plant: SavedPlant; dueInDays: number }[];
}

interface GardenContextValue {
  plants: SavedPlant[];
  isLoaded: boolean;
  addPlant: (result: ScanResult, nickname?: string) => SavedPlant;
  waterPlant: (id: string) => void;
  removePlant: (id: string) => void;
  stats: GardenStats;
}

const GardenContext = createContext<GardenContextValue | undefined>(undefined);

function daysSince(timestamp: number): number {
  return (Date.now() - timestamp) / DAY;
}

export function GardenProvider({ children }: { children: React.ReactNode }) {
  const [plants, setPlants] = useState<SavedPlant[]>([]);
  const [isLoaded, setIsLoaded] = useState(false);

  useEffect(() => {
    (async () => {
      try {
        const raw = await AsyncStorage.getItem(STORAGE_KEY);
        if (raw) {
          setPlants(JSON.parse(raw));
        } else {
          const seed = buildSeedGarden();
          setPlants(seed);
          await AsyncStorage.setItem(STORAGE_KEY, JSON.stringify(seed));
        }
      } catch {
        setPlants(buildSeedGarden());
      } finally {
        setIsLoaded(true);
      }
    })();
  }, []);

  useEffect(() => {
    if (!isLoaded) return;
    AsyncStorage.setItem(STORAGE_KEY, JSON.stringify(plants)).catch(() => {});
  }, [plants, isLoaded]);

  const addPlant = (result: ScanResult, nickname?: string): SavedPlant => {
    const now = Date.now();
    const newPlant: SavedPlant = {
      ...result,
      id: `${result.id}-${now}`,
      nickname,
      savedAt: now,
      lastWateredAt: now,
    };
    setPlants((prev) => [newPlant, ...prev]);
    return newPlant;
  };

  const waterPlant = (id: string) => {
    setPlants((prev) =>
      prev.map((p) => (p.id === id ? { ...p, lastWateredAt: Date.now() } : p))
    );
  };

  const removePlant = (id: string) => {
    setPlants((prev) => prev.filter((p) => p.id !== id));
  };

  const stats = useMemo<GardenStats>(() => {
    const needsWaterToday = plants.filter(
      (p) => daysSince(p.lastWateredAt) >= p.wateringFrequencyDays
    );
    const withIssues = plants.filter((p) => p.health !== 'saludable');
    const upcoming = plants
      .map((plant) => ({
        plant,
        dueInDays: Math.ceil(plant.wateringFrequencyDays - daysSince(plant.lastWateredAt)),
      }))
      .sort((a, b) => a.dueInDays - b.dueInDays)
      .slice(0, 5);

    return { total: plants.length, needsWaterToday, withIssues, upcoming };
  }, [plants]);

  const value: GardenContextValue = {
    plants,
    isLoaded,
    addPlant,
    waterPlant,
    removePlant,
    stats,
  };

  return <GardenContext.Provider value={value}>{children}</GardenContext.Provider>;
}

export function useGarden(): GardenContextValue {
  const ctx = useContext(GardenContext);
  if (!ctx) throw new Error('useGarden must be used within a GardenProvider');
  return ctx;
}
