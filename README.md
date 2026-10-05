# AerolineaVS
Proyecto Aerolínea con Visual Studio Code + Maven + GitHub.

## Requisitos implementados
- CLI en Java para recomendar la tarifa más adecuada según reglas de negocio.
- Gestión con Maven.
- Tests con JUnit 5 y ejecución con Surefire.
- Cobertura con JaCoCo.
- Javadoc en código y generación vía Maven.

## Ejecutar tests
```bash
mvn test
```

## Generar informes de testing
```bash
mvn site:site
```

## Generar Javadoc
```bash
mvn javadoc:javadoc
```

## Ejecutar la CLI
```bash
mvn exec:java -Dexec.mainClass=com.aerolineavs.tarifas.App
```

## Suposiciones para evitar ambigüedades
- En la tarifa de estudiante, "al menos una vez al mes durante el curso" se interpreta como **9 viajes/año** (nueve meses lectivos).
- La frecuencia anual informada cuenta los viajes que cualifican para la regla evaluada: en jóvenes profesionales, son viajes de ocio; en estudiantes, desplazamientos entre la residencia familiar y la universidad durante el curso.
- Para jóvenes profesionales, los requisitos de tres viajes anuales de ocio y clase turista se aplican tanto si viven con sus padres como si ya se han independizado; solo cambia cuál de las dos tarifas se asigna según la convivencia.
- La tarifa "con tus peques" sustituye a la tarifa estándar de Europa o Mundo cuando el cliente viaja con niños menores de 12 años. Se selecciona esa tarifa aunque su descuento por billete sea menor que el de la tarifa estándar; el 10% se aplica a cada billete.
- Las categorías de edad y tipo de viajero, y las condiciones de destino, clase e ingresos, hacen que las reglas sean excluyentes. La evaluación devuelve exactamente una tarifa o "Sin tarifa aplicable" si no se cumplen todos los requisitos.
- Los datos numéricos no pueden ser negativos; los ingresos deben ser finitos y los campos enumerados son obligatorios.
