# Lab Registry Service v2

API REST para la gestión de laboratorios desarrollada con Spring Boot.

## Características

- Registro, consulta, actualización y eliminación de laboratorios (CRUD)
- Búsqueda por nombre y especialidad
- Documentación OpenAPI/Swagger
- Integración con Oracle DB

## Requisitos

- Java 21
- Maven 3
- Docker y Docker Compose (opcional)

## Instalación y ejecución local

1. Compila el proyecto:
   ```bash
   mvn clean package -DskipTests
   ```
2. Ejecuta el JAR:
   ```bash
   java -jar target/lab-registry-service-v2-1.0-SNAPSHOT.jar
   ```

## Variables de entorno

Configura la conexión a la base de datos por variables de entorno o en `application.properties`:

- `DB_URL` (ejemplo: `jdbc:oracle:thin:@//host:port/service`)
- `DB_USERNAME`
- `DB_PASSWORD`

## Docker

1. Construye la imagen:
   ```bash
   docker build -t lab-registry-service-v2:latest .
   ```
2. Ejecuta el contenedor:
   ```bash
   docker run -d \
     -p 8080:8080 \
     -e DB_URL='jdbc:oracle:thin:@//host:port/service' \
     -e DB_USERNAME='dbuser' \
     -e DB_PASSWORD='secret' \
     --name lab-registry-service-v2 \
     lab-registry-service-v2:latest
   ```

## Docker Compose

1. Crea un archivo `.env` junto a tu `docker-compose.yml`:
   ```env
   DB_URL=jdbc:oracle:thin:@//host:port/service
   DB_USERNAME=dbuser
   DB_PASSWORD=secret
   ```
2. Levanta el servicio:
   ```bash
   docker-compose up -d
   ```

## Endpoints principales

| Método | Endpoint               | Descripción                      |
| ------ | ---------------------- | -------------------------------- |
| POST   | `/laboratories`        | Registrar laboratorio            |
| GET    | `/laboratories`        | Listar todos los laboratorios    |
| GET    | `/laboratories/{id}`   | Obtener laboratorio por ID       |
| PUT    | `/laboratories/{id}`   | Actualizar laboratorio           |
| DELETE | `/laboratories/{id}`   | Eliminar laboratorio             |
| GET    | `/laboratories/search` | Buscar por nombre o especialidad |

### Ejemplo de registro de laboratorio

```json
{
  "name": "LabTest",
  "address": "Av. Principal 123",
  "phone": "123456789",
  "email": "lab@test.com",
  "website": "www.labtest.com",
  "specialty": "Bioquímica"
}
```

## Ver logs del contenedor

```bash
docker logs -f lab-registry-service-v2
```
