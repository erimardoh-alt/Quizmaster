package com.example.data.model

object PresetQuestions {

  val allQuestions: List<TriviaQuestion> = listOf(
    // ------------------- NIVEL 1: PRINCIPIANTE (ORGANIC & INORGANIC) -------------------
    TriviaQuestion(
      id = "org_beg_1",
      compoundName = "Metano",
      formula = "CH₄",
      type = CompoundType.ORGANIC,
      difficulty = DifficultyLevel.BEGINNER,
      questionText = "¿Cuál es la geometría molecular y el tipo de hibridación del átomo de carbono en el metano (CH₄)?",
      options = listOf(
        "Tetraédrica con hibridación sp³",
        "Trigonal plana con hibridación sp²",
        "Lineal con hibridación sp",
        "Piramidal trigonal con hibridación sp³d"
      ),
      correctIndex = 0,
      explanation = "El metano (CH₄) tiene 4 enlaces simples C-H equivalentes. Según el modelo RPECV, los 4 pares de electrones enlazantes se repelen hacia los vértices de un tetraedro regular con ángulos de 109.5°, lo que corresponde a una hibridación orbital sp³ del carbono central.",
      chemicalFact = "El metano es el hidrocarburo alcano más simple y el componente principal (>85%) del gas natural.",
      reactionEquation = "CH₄ + 2O₂ → CO₂ + 2H₂O + 891 kJ/mol",
      hint = "El carbono comparte 4 pares de electrones sin electrones libres."
    ),
    TriviaQuestion(
      id = "inorg_beg_1",
      compoundName = "Ácido Sulfúrico",
      formula = "H₂SO₄",
      type = CompoundType.INORGANIC,
      difficulty = DifficultyLevel.BEGINNER,
      questionText = "¿Qué estado de oxidación posee el azufre en el ácido sulfúrico (H₂SO₄)?",
      options = listOf(
        "+4",
        "+6",
        "+2",
        "-2"
      ),
      correctIndex = 1,
      explanation = "En el H₂SO₄, el hidrógeno actúa con +1 (total +2) y el oxígeno con -2 (total -8). Para que la molécula sea eléctricamente neutra: (+2) + S + 4(-2) = 0, despejando obtenemos que el azufre tiene estado de oxidación +6.",
      chemicalFact = "El H₂SO₄ es conocido como el 'rey de los productos químicos' porque su producción anual mundial es un indicador clave del desarrollo industrial de un país.",
      reactionEquation = "SO₃ + H₂O → H₂SO₄ (altamente exotérmica)",
      hint = "El oxígeno aporta -8 y dos hidrógenos aportan +2."
    ),
    TriviaQuestion(
      id = "org_beg_2",
      compoundName = "Etanol",
      formula = "C₂H₅OH",
      type = CompoundType.ORGANIC,
      difficulty = DifficultyLevel.BEGINNER,
      questionText = "¿A qué grupo funcional orgánico pertenece el compuesto CH₃-CH₂-OH?",
      options = listOf(
        "Éter (-O-)",
        "Alcohol (-OH)",
        "Aldehído (-CHO)",
        "Ácido Carboxílico (-COOH)"
      ),
      correctIndex = 1,
      explanation = "El etanol contiene un grupo hidroxilo (-OH) enlazado a un carbono saturado (sp³). Por tanto, se clasifica como un alcohol primario monovalente. Su presencia le confiere la capacidad de formar puentes de hidrógeno intermoleculares, elevando su punto de ebullición (78.37 °C).",
      chemicalFact = "Es el alcohol presente en las bebidas fermentadas y se utiliza globalmente como antiséptico y biocombustible renovable.",
      reactionEquation = "C₆H₁₂O₆ (fermentación) → 2 C₂H₅OH + 2 CO₂",
      hint = "Termina en sufijo -ol y tiene un grupo hidroxilo."
    ),
    TriviaQuestion(
      id = "inorg_beg_2",
      compoundName = "Hidróxido de Sodio",
      formula = "NaOH",
      type = CompoundType.INORGANIC,
      difficulty = DifficultyLevel.BEGINNER,
      questionText = "¿Cómo se clasifica químicamente la sosa cáustica (NaOH) en disolución acuosa?",
      options = listOf(
        "Base fuerte que se disocia completamente",
        "Ácido monoprótico débil",
        "Sal neutra insoluble",
        "Óxido ácido anfótero"
      ),
      correctIndex = 0,
      explanation = "El NaOH es una base de Arrhenius y de Brønsted-Lowry fuerte. Al disolverse en agua se disocia al 100% en cationes sodio (Na⁺) y aniones hidróxido (OH⁻), generando un pH altamente alcalino (>13).",
      chemicalFact = "Es el reactivo clave en la saponificación de triglicéridos para fabricar jabones clásicos.",
      reactionEquation = "NaOH (s) + H₂O → Na⁺(aq) + OH⁻(aq)",
      hint = "Aporta cationes Na⁺ e iones hidroxilo OH⁻ al disolverse."
    ),
    TriviaQuestion(
      id = "org_beg_3",
      compoundName = "Ácido Acético",
      formula = "CH₃COOH",
      type = CompoundType.ORGANIC,
      difficulty = DifficultyLevel.BEGINNER,
      questionText = "¿Cuál es el nombre sistemático IUPAC del ácido acético (CH₃COOH), componente del vinagre común?",
      options = listOf(
        "Ácido metanoico",
        "Ácido etanoico",
        "Ácido propanoico",
        "Ácido butanoico"
      ),
      correctIndex = 1,
      explanation = "La molécula posee 2 átomos de carbono con una función terminal carboxilo (-COOH). La raíz de 2 carbonos es 'etán-', por lo que según las reglas de nomenclatura IUPAC su nombre sistemático es ácido etanoico.",
      chemicalFact = "El vinagre doméstico contiene entre un 4% y 8% de ácido acético disuelto en agua.",
      reactionEquation = "CH₃COOH ⇌ CH₃COO⁻ + H⁺ (Ka = 1.8 × 10⁻⁵)",
      hint = "Posee dos átomos de carbono en su cadena principal."
    ),
    TriviaQuestion(
      id = "inorg_beg_3",
      compoundName = "Cloruro de Sodio",
      formula = "NaCl",
      type = CompoundType.INORGANIC,
      difficulty = DifficultyLevel.BEGINNER,
      questionText = "¿Qué tipo de enlace químico mantiene unidos a los átomos en la red cristalina de la sal común (NaCl)?",
      options = listOf(
        "Enlace iónico (electrovalente)",
        "Enlace covalente no polar",
        "Enlace metálico",
        "Fuerzas de dispersión de London exclusivamente"
      ),
      correctIndex = 0,
      explanation = "El sodio (Na, metal alcalino con baja electronegatividad de 0.93) transfiere su electrón de valencia al cloro (Cl, halógeno muy electronegativo con 3.16). La gran diferencia de electronegatividad (ΔEN = 2.23 > 1.7) genera atracción electrostática entre iones Na⁺ y Cl⁻ en una red cúbica centrada en las caras.",
      chemicalFact = "Cada catión Na⁺ en la halita está coordinado octaédricamente con 6 aniones Cl⁻, y viceversa.",
      reactionEquation = "2Na(s) + Cl₂(g) → 2NaCl(s) (altamente luminosa y exotérmica)",
      hint = "Ocurre entre un metal alcalino y un halógeno no metal."
    ),

    // ------------------- NIVEL 2: INTERMEDIO (ORGANIC & INORGANIC) -------------------
    TriviaQuestion(
      id = "org_int_1",
      compoundName = "Benceno",
      formula = "C₆H₆",
      type = CompoundType.ORGANIC,
      difficulty = DifficultyLevel.INTERMEDIATE,
      questionText = "¿Por qué el benceno (C₆H₆) no experimenta fácilmente reacciones de adición típicas de los alquenos?",
      options = listOf(
        "Por su extraordinaria estabilidad conferida por la deslocalización aromática (energía de resonancia)",
        "Porque carece de enlaces pi (π) en su estructura",
        "Porque todos sus átomos de carbono tienen hibridación sp³",
        "Porque tiene una tensión de anillo angular demasiado elevada"
      ),
      correctIndex = 0,
      explanation = "El anillo de benceno posee 6 electrones pi deslocalizados sobre un ciclo plano de 6 carbonos sp² que cumple la Regla de Hückel (4n + 2 = 6 con n=1). La energía de resonancia (~150 kJ/mol) hace que las reacciones de adición destruyan la aromaticidad; por ello prefiere la sustitución electrofílica aromática (SEAr).",
      chemicalFact = "August Kekulé dedujo la estructura anular tras soñar con una serpiente que se mordía la cola (el mítico Ouroboros).",
      reactionEquation = "C₆H₆ + Br₂ (con FeBr₃) → C₆H₅Br + HBr (Sustitución, no adición)",
      hint = "Involucra la regla de 4n+2 electrones pi y energía de resonancia."
    ),
    TriviaQuestion(
      id = "inorg_int_1",
      compoundName = "Permanganato de Potasio",
      formula = "KMnO₄",
      type = CompoundType.INORGANIC,
      difficulty = DifficultyLevel.INTERMEDIATE,
      questionText = "En medio fuertemente ácido, ¿en qué especie se reduce el anión permanganato (MnO₄⁻) de color violeta intenso?",
      options = listOf(
        "Mn²⁺ (incoloro o rosa muy pálido)",
        "MnO₂ (precipitado marrón oscuro)",
        "MnO₄²⁻ (anión manganato verde)",
        "Mn(OH)₃ (gelatinoso)"
      ),
      correctIndex = 0,
      explanation = "En titulación redox en medio ácido (usualmente H₂SO₄ diluido), el manganeso pasa de estado de oxidación +7 a +2 mediante la semirreacción: MnO₄⁻ + 8H⁺ + 5e⁻ → Mn²⁺ + 4H₂O. Por ello, sirve como su propio autoindicador en volumetrías.",
      chemicalFact = "El color violeta profundo del MnO₄⁻ no proviene de transiciones d-d clásicas, sino de una transferencia de carga ligando a metal (LMCT).",
      reactionEquation = "MnO₄⁻ + 8H⁺ + 5e⁻ → Mn²⁺ + 4H₂O (E° = +1.51 V)",
      hint = "El manganeso pasa de estado de oxidación +7 al catión divalente."
    ),
    TriviaQuestion(
      id = "org_int_2",
      compoundName = "Acetona",
      formula = "CH₃COCH₃",
      type = CompoundType.ORGANIC,
      difficulty = DifficultyLevel.INTERMEDIATE,
      questionText = "¿Cuál es el resultado de la oxidación controlada de un alcohol secundario como el 2-propanol?",
      options = listOf(
        "Una cetona (Propanona o Acetona)",
        "Un aldehído (Propanal)",
        "Un éster metílico",
        "Un ácido tricarboxílico"
      ),
      correctIndex = 0,
      explanation = "Al oxidar un alcohol secundario con reactivos como el dicromato o el reactivo de Jones, el carbono que porta el grupo hidroxilo pierde su hidrógeno y se forma un doble enlace C=O (grupo carbonilo no terminal), dando como resultado una cetona (propanona o acetona).",
      chemicalFact = "A nivel industrial, más del 80% de la acetona mundial se obtiene como coproducto junto al fenol a través del proceso del cumeno.",
      reactionEquation = "CH₃-CH(OH)-CH₃ + [O] → CH₃-CO-CH₃ + H₂O",
      hint = "El carbono del hidroxilo está unido a otros dos carbonos."
    ),
    TriviaQuestion(
      id = "inorg_int_2",
      compoundName = "Sulfato de Cobre(II)",
      formula = "CuSO₄ · 5H₂O",
      type = CompoundType.INORGANIC,
      difficulty = DifficultyLevel.INTERMEDIATE,
      questionText = "¿Por qué los cristales de sulfato de cobre pentahidratado (CuSO₄·5H₂O) son de color azul brillante, mientras que el CuSO₄ anhidro es blanco?",
      options = listOf(
        "Porque las moléculas de agua actúan como ligandos coordinados desdoblando los orbitales d del Cu²⁺",
        "Porque el agua se oxida a oxígeno molecular absorbiendo luz",
        "Porque el cobre metálico se deposita en la superficie",
        "Por la presencia de impurezas radioactivas de sulfuro"
      ),
      correctIndex = 0,
      explanation = "El ion Cu²⁺ tiene configuración electrónica [Ar] 3d⁹. Al coordinarse con 4 moléculas de agua en el plano ecuatorial (y una quinta unida por puente de hidrógeno), el campo cristalino desdobla los orbitales d. La absorción de fotones en la región roja del espectro visible promueve electrones d-d, reflejando el característico color azul zafiro.",
      chemicalFact = "El CuSO₄ anhidro blanco se usa en los laboratorios como detector químico sensible para trazas de humedad en disolventes.",
      reactionEquation = "CuSO₄(anhidro blanco) + 5H₂O → CuSO₄·5H₂O(cristal azul)",
      hint = "Efecto de campo cristalino y transiciones d-d del catión Cu²⁺."
    ),
    TriviaQuestion(
      id = "org_int_3",
      compoundName = "Formaldehído",
      formula = "HCHO",
      type = CompoundType.ORGANIC,
      difficulty = DifficultyLevel.INTERMEDIATE,
      questionText = "¿Qué prueba de laboratorio clásica da positiva (espejo de plata) para aldehídos como el metanal (HCHO)?",
      options = listOf(
        "Reactivo de Tollens ([Ag(NH₃)₂]⁺)",
        "Prueba de Lucas (ZnCl₂ en HCl)",
        "Prueba de Baeyer con permanganato alcalino",
        "Prueba de Biuret para péptidos"
      ),
      correctIndex = 0,
      explanation = "El reactivo de Tollens contiene el complejo catiónico diaminsolubilizado [Ag(NH₃)₂]⁺. Los aldehídos son fácilmente oxidables a ácidos carboxílicos, reduciendo la plata monovalente a plata metálica elemental (Ag⁰), la cual precipita formando un reluciente 'espejo de plata' en las paredes del tubo.",
      chemicalFact = "El formaldehído en solución acuosa al 37% estabilizada con metanol se denomina comercialmente formol.",
      reactionEquation = "R-CHO + 2[Ag(NH₃)₂]⁺ + 3OH⁻ → R-COO⁻ + 2Ag(s)↓ + 4NH₃ + 2H₂O",
      hint = "Implica iones de plata en medio amoniacal que forman un espejo."
    ),
    TriviaQuestion(
      id = "inorg_int_3",
      compoundName = "Amoníaco",
      formula = "NH₃",
      type = CompoundType.INORGANIC,
      difficulty = DifficultyLevel.INTERMEDIATE,
      questionText = "¿Cuál es la geometría molecular real del amoníaco (NH₃) y el valor aproximado de su ángulo de enlace H-N-H?",
      options = listOf(
        "Piramidal trigonal (~107°)",
        "Tetraédrica perfecta (109.5°)",
        "Trigonal plana (120°)",
        "Forma de T (90°)"
      ),
      correctIndex = 0,
      explanation = "El nitrógeno tiene 5 electrones de valencia: forma 3 enlaces simples N-H y retiene 1 par de electrones no enlazantes. La geometría electrónica es tetraédrica, pero la geometría molecular es piramidal trigonal. La mayor repulsión del par solitario comprime los ángulos de enlace de 109.5° a 107.3°.",
      chemicalFact = "Fritz Haber y Carl Bosch desarrollaron la síntesis catalítica de NH₃ a alta presión y temperatura, galardonada con sendos Premios Nobel.",
      reactionEquation = "N₂(g) + 3H₂(g) ⇌ 2NH₃(g) (ΔH = -92.4 kJ/mol con catalizador de Fe)",
      hint = "Tiene un par de electrones libres que comprime el ángulo tetraédrico."
    ),

    // ------------------- NIVEL 3: AVANZADO (ORGANIC & INORGANIC) -------------------
    TriviaQuestion(
      id = "org_adv_1",
      compoundName = "Acetileno (Etino)",
      formula = "C₂H₂",
      type = CompoundType.ORGANIC,
      difficulty = DifficultyLevel.ADVANCED,
      questionText = "¿Por qué los hidrógenos terminales de los alquinos terminales como el etino (H-C≡C-H) son significativamente más ácidos (pKa ~25) que los de los alquenos (pKa ~44) y alcanos (pKa ~50)?",
      options = listOf(
        "Por la mayor proporción de carácter 's' (50%) en el orbital híbrido sp del carbono, estabilizando la base conjugada",
        "Por la presencia de dos dobles enlaces conjugados resonantes",
        "Porque el triple enlace disminuye la electronegatividad del carbono",
        "Porque el etino se autoioniza formando iones carbonio estables"
      ),
      correctIndex = 0,
      explanation = "El orbital híbrido sp tiene 50% de carácter s (frente al 33% del sp² y 25% del sp³). Como los electrones en orbitales s se encuentran más próximos al núcleo con carga positiva, el carbanión acetiluro resultante (H-C≡C⁻) retiene el par solitario con mayor fuerza electrostática, lo que estabiliza fuertemente la base conjugada y aumenta su acidez.",
      chemicalFact = "El acetileno genera una llama con oxígeno puro que supera los 3300 °C en los sopletes de oxicorte.",
      reactionEquation = "H-C≡C-H + NaNH₂ (en NH₃ líquido) → H-C≡C⁻ Na⁺ + NH₃",
      hint = "A mayor carácter 's', los electrones están más cerca del núcleo atómico."
    ),
    TriviaQuestion(
      id = "inorg_adv_1",
      compoundName = "Dicromato de Potasio",
      formula = "K₂Cr₂O₇",
      type = CompoundType.INORGANIC,
      difficulty = DifficultyLevel.ADVANCED,
      questionText = "¿Qué ocurre con el equilibrio entre el ion dicromato (Cr₂O₇²⁻, naranja) y el ion cromato (CrO₄²⁻, amarillo) cuando se agrega una base fuerte?",
      options = listOf(
        "Se desplaza hacia la formación del ion cromato (amarillo, CrO₄²⁻)",
        "Se precipita cromo metálico elemental",
        "Se descompone violentamente liberando gas cloro",
        "Permanece inalterado porque no involucra protones H⁺"
      ),
      correctIndex = 0,
      explanation = "El equilibrio químico es: Cr₂O₇²⁻ (naranja) + H₂O ⇌ 2CrO₄²⁻ (amarillo) + 2H⁺. Al agregar una base (iones OH⁻), estos neutralizan los protones H⁺ formando agua. Por el Principio de Le Chatelier, el equilibrio se desplaza a la derecha, transformando la disolución naranja en amarillo brillante.",
      chemicalFact = "El cromo en ambos iones se encuentra en estado de oxidación +6, por lo que es un cambio de equilibrio ácido-base y NO una reacción redox.",
      reactionEquation = "Cr₂O₇²⁻ + 2OH⁻ ⇌ 2CrO₄²⁻ + H₂O",
      hint = "El principio de Le Chatelier al consumir protones H⁺ con la base."
    ),
    TriviaQuestion(
      id = "org_adv_2",
      compoundName = "Ácido Acetilsalicílico",
      formula = "C₉H₈O₄",
      type = CompoundType.ORGANIC,
      difficulty = DifficultyLevel.ADVANCED,
      questionText = "¿Qué reactivo se utiliza comúnmente junto con el ácido salicílico y ácido sulfúrico concentrado para sintetizar Aspirina en el laboratorio?",
      options = listOf(
        "Anhídrido acético ((CH₃CO)₂O)",
        "Metanol anhidro",
        "Formiato de etilo",
        "Tetracloruro de carbono"
      ),
      correctIndex = 0,
      explanation = "La síntesis de la aspirina (ácido acetilsalicílico) es una reacción de esterificación del grupo hidroxilo fenólico del ácido salicílico. Se emplea anhídrido acético catalizado por H₂SO₄ o H₃PO₄ porque es mucho más reactivo que el ácido acético y no produce agua (evitando la hidrólisis inversa del éster).",
      chemicalFact = "Felix Hoffmann sintetizó por primera vez el ácido acetilsalicílico puro y estable en los laboratorios Bayer en 1897.",
      reactionEquation = "C₇H₆O₃ (ác. salicílico) + (CH₃CO)₂O → C₉H₈O₄ (aspirina) + CH₃COOH",
      hint = "Es un anhídrido de ácido carboxílico que acetila el grupo fenol."
    ),
    TriviaQuestion(
      id = "inorg_adv_2",
      compoundName = "Cisplatino",
      formula = "[Pt(NH₃)₂Cl₂]",
      type = CompoundType.INORGANIC,
      difficulty = DifficultyLevel.ADVANCED,
      questionText = "¿Cuál es la geometría del complejo de coordinación cisplatino (cis-[Pt(NH₃)₂Cl₂]) y por qué su isómero trans- no tiene actividad anticancerígena?",
      options = listOf(
        "Cuadrada plana; solo el isómero cis puede unirse a dos bases guanina adyacentes del ADN formando aductos intracadena",
        "Tetraédrica; el isómero trans es químicamente inerte",
        "Octaédrica con 2 vacantes que repelen nucleótidos",
        "Piramidal de base cuadrada insoluble en suero sanguíneo"
      ),
      correctIndex = 0,
      explanation = "El Pt(II) es un ion d⁸ que forma complejos cuadrado planos de bajo espín con hibridación dsp². El cisplatino tiene ambos ligandos cloro en posición contigua (ángulo de 90°), permitiendo unirse a las posiciones N7 de dos guaninas vecinas en la misma hebra del ADN bicatenario, distorsionando la hélice e induciendo apoptosis.",
      chemicalFact = "Barnett Rosenberg descubrió accidentalmente las propiedades citostáticas del platino en 1965 mientras investigaba el efecto de campos eléctricos en bacterias E. coli.",
      reactionEquation = "cis-[Pt(NH₃)₂Cl₂] + 2H₂O → cis-[Pt(NH₃)₂ (H₂O)₂]²⁺ + 2Cl⁻",
      hint = "Geometría cuadrada plana con ángulo de 90° entre los dos ligandos cloro."
    ),
    TriviaQuestion(
      id = "org_adv_3",
      compoundName = "Glicina",
      formula = "C₂H₅NO₂",
      type = CompoundType.ORGANIC,
      difficulty = DifficultyLevel.ADVANCED,
      questionText = "¿Por qué la glicina (NH₂-CH₂-COOH) es el único aminoácido proteinogénico estándar que no posee actividad óptica (no es quiral)?",
      options = listOf(
        "Porque su carbono alfa está unido a dos átomos de hidrógeno idénticos",
        "Porque tiene un plano de simetría debido al nitrógeno terciario",
        "Porque forma un enlace peptídico intramolecular cíclico",
        "Porque solo existe en disolventes no polares"
      ),
      correctIndex = 0,
      explanation = "Un centro quiral o estereocentro requiere 4 sustituyentes diferentes unidos a un carbono sp³. En la glicina, el carbono alfa está enlazado a un grupo amino (-NH₂), un grupo carboxilo (-COOH) y DOS átomos de hidrógeno (-H, -H). Al tener dos ligandos idénticos, su imagen especular es superponible con la original.",
      chemicalFact = "A pH fisiológico neutro (7.4), la glicina existe casi en su totalidad como un zwitterion dipolar: ⁺H₃N-CH₂-COO⁻.",
      reactionEquation = "⁺H₃N-CH₂-COO⁻ (Zwitterion de carga neta cero, pI = 5.97)",
      hint = "El carbono alfa está unido a dos sustituyentes exactamente iguales."
    ),
    TriviaQuestion(
      id = "inorg_adv_3",
      compoundName = "Ácido Nítrico",
      formula = "HNO₃",
      type = CompoundType.INORGANIC,
      difficulty = DifficultyLevel.ADVANCED,
      questionText = "¿En qué proporción en volumen se mezclan el ácido clorhídrico concentrado (HCl) y el ácido nítrico concentrado (HNO₃) para formar el 'agua regia' capaz de disolver el oro?",
      options = listOf(
        "3 partes de HCl concentrado y 1 parte de HNO₃ concentrado (3:1)",
        "1 parte de HCl y 1 parte de HNO₃ (1:1)",
        "1 parte de HCl y 3 partes de HNO₃ (1:3)",
        "4 partes de HCl y 2 partes de H₂SO₄"
      ),
      correctIndex = 0,
      explanation = "El agua regia se formula con 3 volúmenes de HCl (~37%) por 1 volumen de HNO₃ (~65%). El HNO₃ oxida al oro a Au³⁺, mientras que los aniones cloruro libres del HCl forman el anión complejo tetracloroaurato(III) [AuCl₄]⁻ muy estable, retirando los iones Au³⁺ de disolución y desplazando el potencial redox.",
      chemicalFact = "Durante la Segunda Guerra Mundial, George de Hevesy disolvió en agua regia las medallas Nobel de Max von Laue y James Franck para ocultarlas de los nazis, recuperando el oro intacto tras la guerra.",
      reactionEquation = "Au + 3HNO₃ + 4HCl → H[AuCl₄] + 3NO₂ + 3H₂O",
      hint = "Tres partes del ácido hidrácido por una parte del ácido oxácido."
    ),

    // ------------------- NIVEL 4: EXPERTO (ORGANIC & INORGANIC) -------------------
    TriviaQuestion(
      id = "org_exp_1",
      compoundName = "Glucosa",
      formula = "C₆H₁₂O₆",
      type = CompoundType.ORGANIC,
      difficulty = DifficultyLevel.EXPERT,
      questionText = "En la ciclación de la D-glucosa a glucopiranosa en solución acuosa, ¿cómo se denominan los dos estereoisómeros que difieren únicamente en la configuración del nuevo centro estereogénico (carbono anomérico C-1)?",
      options = listOf(
        "Anómeros alfa (α) y beta (β)",
        "Enantiómeros dextro (D) y levo (L)",
        "Constitucionales de posición",
        "Meso-compuestos aquirales"
      ),
      correctIndex = 0,
      explanation = "El ataque nucleofílico intramolecular del grupo -OH del carbono 5 sobre el carbonilo aldehídico en C-1 forma un hemiacetal cíclico de seis miembros (anillo piranósico). El C-1 se convierte en un centro quiral denominado 'carbono anomérico'. Si el -OH anomérico queda trans respecto al -CH₂OH es el anómero α, y si queda cis es el anómero β.",
      chemicalFact = "La interconversión espontánea entre ambos anómeros a través de la forma de cadena abierta se conoce como mutarrotación, alcanzando un equilibrio óptico de +52.7°.",
      reactionEquation = "D-Glucosa (cadena abierta) ⇌ α-D-Glucopiranosa (36%) + β-D-Glucopiranosa (64%)",
      hint = "Difieren en la orientación del grupo -OH en el carbono carbonílico hemiacetálico."
    ),
    TriviaQuestion(
      id = "inorg_exp_1",
      compoundName = "Complejo de Vaska",
      formula = "IrCl(CO)[P(C₆H₅)₃]₂",
      type = CompoundType.INORGANIC,
      difficulty = DifficultyLevel.EXPERT,
      questionText = "¿Qué tipo de reacción organometálica fundamental experimenta el complejo de Vaska (trans-[IrCl(CO)(PPh₃)₂]) al reaccionar reversiblemente con oxígeno molecular (O₂) o hidrógeno (H₂)?",
      options = listOf(
        "Adición oxidativa con aumento de 2 unidades en el estado de oxidación y en el número de coordinación",
        "Eliminación reductora bimolecular",
        "Inserción migratoria 1,1 de monóxido de carbono",
        "Sustitución disociativa Sn1 con pérdida de trifenilfosfina"
      ),
      correctIndex = 0,
      explanation = "El complejo de Vaska es un complejo plano cuadrado de 16 electrones con Iridio(I) d⁸. Al reaccionar con H₂ u O₂, incorpora la molécula diatómica pasando a ser un complejo octaédrico de 18 electrones con Iridio(III) d⁶. Por tanto, el metal se oxida (Ir(I) → Ir(III)) y su número de coordinación aumenta de 4 a 6 en una adición oxidativa.",
      chemicalFact = "Lauri Vaska sintetizó este hito en 1961, sirviendo de prototipo para comprender cómo las proteínas como la mioglobina transportan O₂ sin desintegrarse.",
      reactionEquation = "trans-[IrCl(CO)(PPh₃)₂] (16e⁻, Ir⁺¹) + H₂ ⇌ cis-[Ir(H)₂Cl(CO)(PPh₃)₂] (18e⁻, Ir⁺³)",
      hint = "El átomo de iridio se oxida de +1 a +3 mientras añade dos ligandos."
    ),
    TriviaQuestion(
      id = "org_exp_2",
      compoundName = "Tolueno (Metilbenceno)",
      formula = "C₇H₈",
      type = CompoundType.ORGANIC,
      difficulty = DifficultyLevel.EXPERT,
      questionText = "¿Cuál es el efecto orientador y de activación del grupo metilo (-CH₃) en la nitración electrofílica del tolueno?",
      options = listOf(
        "Activa el anillo y orienta hacia posiciones orto y para mediante hiperconjugación e inductivo dador",
        "Desactiva el anillo y orienta exclusivamente a meta por efecto mesomérico sustractor",
        "Activa el anillo y orienta exclusivamente a la posición meta",
        "Desactiva el anillo pero orienta a orto y para por efecto de resonancia"
      ),
      correctIndex = 0,
      explanation = "El grupo alquilo -CH₃ dona densidad electrónica al sistema pi del anillo aromático mediante efecto inductivo (+I) y, primordialmente, por hiperconjugación (traslape de orbitales sigma C-H con los orbitales p del catión ciclohexadienilo intermedio). Esto dispersa la carga positiva en los complejos sigma de las posiciones orto y para.",
      chemicalFact = "La trinitración exhaustiva del tolueno con mezcla sulfonítrica concentrada produce el potente explosivo 2,4,6-trinitrotolueno (TNT).",
      reactionEquation = "C₆H₅-CH₃ + HNO₃ (con H₂SO₄) → o-nitrotolueno (59%) + p-nitrotolueno (37%) + m-nitrotolueno (4%)",
      hint = "Dona densidad de carga y estabiliza el carbocatión en las posiciones orto y para."
    ),
    TriviaQuestion(
      id = "inorg_exp_2",
      compoundName = "Peróxido de Hidrógeno",
      formula = "H₂O₂",
      type = CompoundType.INORGANIC,
      difficulty = DifficultyLevel.EXPERT,
      questionText = "¿Qué tipo de reacción redox experimenta el peróxido de hidrógeno en presencia de catalizadores como dióxido de manganeso (MnO₂) o la enzima catalasa?",
      options = listOf(
        "Dismutación o desproporción (el oxígeno a la vez se oxida y se reduce)",
        "Comproporción sin cambio en la entalpía",
        "Reducción pura sin desprendimiento gaseoso",
        "Sublimación química endotérmica"
      ),
      correctIndex = 0,
      explanation = "En el H₂O₂, el oxígeno tiene un estado de oxidación anómalo de -1. Al descomponerse catalíticamente: 2H₂O₂ → 2H₂O + O₂, parte de los átomos de oxígeno se reducen a -2 (en el agua, H₂O) y la otra parte se oxida a 0 (en el dioxígeno diatómico, O₂). Una reacción donde un mismo elemento se oxida y se reduce simultáneamente se llama dismutación.",
      chemicalFact = "Una sola molécula de la enzima catalasa en nuestras células descompone hasta 40 millones de moléculas de H₂O₂ por segundo, operando al límite de difusión física.",
      reactionEquation = "2 H₂O₂ (O⁻¹) → 2 H₂O (O⁻²) + O₂ (O⁰) + 98 kJ/mol",
      hint = "El mismo átomo de oxígeno se oxida a O₂ y se reduce a H₂O."
    )
  )

  fun getQuestionsByFilter(
    category: CompoundType = CompoundType.ALL,
    difficulty: DifficultyLevel? = null
  ): List<TriviaQuestion> {
    return allQuestions.filter { question ->
      val matchesCategory = when (category) {
        CompoundType.ALL -> true
        else -> question.type == category
      }
      val matchesDiff = difficulty == null || question.difficulty == difficulty
      matchesCategory && matchesDiff
    }
  }
}
