import { getRandomScanResult } from '@/data/mockPlants';
import { ScanResult } from '@/types/plant';

/**
 * Contrato que debe cumplir cualquier proveedor de identificación de plantas.
 * Para conectar una API de IA real, crea una clase que implemente esta
 * interfaz (por ejemplo `RemotePlantIdentificationService`, que envíe
 * `imageUri` a un endpoint propio o a un servicio como Plant.id / PlantNet)
 * y reemplaza la instancia exportada al final de este archivo.
 */
export interface PlantIdentificationService {
  identify(imageUri: string): Promise<ScanResult>;
}

/**
 * Implementación simulada usada en esta primera versión visual.
 * No hace ninguna llamada de red: devuelve un resultado aleatorio de la
 * base de datos local tras una pequeña demora, para imitar el tiempo de
 * respuesta de un análisis real.
 */
class MockPlantIdentificationService implements PlantIdentificationService {
  async identify(_imageUri: string): Promise<ScanResult> {
    await new Promise((resolve) => setTimeout(resolve, 1400));
    return getRandomScanResult();
  }
}

export const plantIdentificationService: PlantIdentificationService =
  new MockPlantIdentificationService();
