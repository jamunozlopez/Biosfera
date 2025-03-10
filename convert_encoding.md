
## Cómo usar el script:

Para convertir un único archivo y sobrescribirlo:

`python convert_encoding.py index.htm`


Para convertir un archivo y guardar el resultado en otro archivo:

`python convert_encoding.py index.htm index_utf8.htm`

Para convertir todos los archivos HTML del directorio actual:

`python convert_encoding.py`

Este script detecta automáticamente la codificación ISO-8859-1 y convierte el texto a UTF-8, solucionando los problemas con caracteres como tildes (á, é, í, ó, ú), eñes (ñ) y otros caracteres especiales que aparecen incorrectamente en tu archivo actual.
