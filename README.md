# Servicio de Autenticación

### Endpoints

- **POST** `/api/auth/register`
- **POST** `/api/auth/login`
- **GET** `/api/auth/me` — requiere incluir una cabecera de **autorización** con un _access token_ no revocado.

### Configuración

El servicio admite las siguientes **variables de entorno opcionales**:

- `DB_USER`
- `DB_PASS`
- `JWT_TOKEN` — cadena hexadecimal de 256 bits en formato de codificación **Base64URL**.

### Probar el servicio

Existen **payloads de prueba** para los endpoints, organizados en carpetas según el nombre del endpoint (`/api/auth/*`, por ejemplo `register`), dentro de la carpeta [`payloads`](./payloads) ubicada en la raíz del proyecto.

Los payloads denominados `default` deberían poder ejecutarse con normalidad (aunque podrían fallar dependiendo de las validaciones o restricciones de integridad en la base de datos).

#### Ejemplo con cURL

Desde la raíz del proyecto:

```bash
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d @payloads/register/validate.json
```