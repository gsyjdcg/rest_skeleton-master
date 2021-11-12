Microservicio con los siguiente endpoints.

/price [GET]
=================

-   Devuelve el precio de un artículo de una cadena en una fecha dada

Cadena de llamada: 
---------

http://localhost:8043/codechallenge/price?applicationDate=2020-06-14-16:00:00&productId=35455&brandId=1


-   **applicationDate** (obligatorio): Fecha para la que se realiza la consulta.
-   **productId** (obligatorio): Identificador único del producto a consultar.
-   **brandId** (obligatorio): Identifica una cadena dentro de Inditex.

El postman de test externo se puede encontrar en la carpeta /postman del proyecto.