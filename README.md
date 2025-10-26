# Servicio de Autenticación

### Endpoints

- **POST** `/api/auth/register`
- **POST** `/api/auth/login`

### Configuración

Hay tres variables de entorno opcionales:

- `DB_USER`
- `DB_PASS`
- `JWT_TOKEN` (cadena hexadecimal de 256 bits)

### Probar el servicio

Existen **payloads de prueba** para los endpoints, organizados en carpetas según el nombre del endpoint (`/api/auth/*`, por ejemplo `register`), dentro de la carpeta [`payloads`](./payloads) ubicada en la raíz del proyecto.

Los payloads con el nombre `default` deberían poder pasar con normalidad (según la validación o restricciones de integridad en la base de datos podría fallar).

A continuación un ejemplo usando el programa cURL desde la raíz del proyecto.

``` bash
curl -X POST http://localhost:8081/api/auth/register -H "Content-Type: application/json" -d @payloads/register/validate.json
```
