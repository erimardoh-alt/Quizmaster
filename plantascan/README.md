# PlantaScan 🌿

Primera versión visual y funcional de **PlantaScan**, una app para identificar
plantas, guardarlas en una colección personal ("Mi Jardín") y consultar
recomendaciones de cuidado. Construida con **React Native + Expo** (Expo
Router), pensada para probarse primero en el navegador y después en un
dispositivo móvil.

## Cómo ejecutarla en local

```bash
cd plantascan
npm install
```

```bash
npx expo start --web
```

Esto abre la app en el navegador. Para probarla en un teléfono, instala la
app **Expo Go** y ejecuta `npx expo start` (sin `--web`) para escanear el
código QR, o `npm run android` / `npm run ios`.

## Estructura del proyecto

```
src/
  app/                  Rutas (Expo Router)
    _layout.tsx         Layout raíz (providers globales)
    (tabs)/
      _layout.tsx       Navegación inferior (3 pestañas)
      index.tsx         Escanear
      jardin.tsx        Mi Jardín
      cuidados.tsx       Cuidados
  components/           Tarjetas y piezas de UI reutilizables
  context/              Estado global de la colección de plantas (persistido)
  data/                 Base de datos simulada de plantas y consejos
  services/             Capa de identificación de plantas
  theme/                Paleta de colores, espaciados y sombras
  types/                Tipos compartidos
```

## Datos simulados y próxima integración con IA

Esta versión **no usa una API de IA real**: el "análisis" de una foto
devuelve un resultado aleatorio de una base de datos local
(`src/data/mockPlants.ts`), con una pequeña demora para simular el tiempo de
respuesta de un servicio real.

Para conectar un proveedor real de identificación de plantas, solo hace
falta tocar un archivo: `src/services/plantIdentificationService.ts`. Crea
una clase que implemente `PlantIdentificationService` (por ejemplo, enviando
la imagen a tu propio backend o a una API como Plant.id/PlantNet) y
reemplaza la instancia exportada al final del archivo. El resto de la app
—pantallas, estado del jardín, estadísticas— no necesita cambios.

La colección de "Mi Jardín" se guarda con `AsyncStorage`, así que persiste
entre sesiones (incluye algunas plantas de ejemplo la primera vez que se
abre la app).
