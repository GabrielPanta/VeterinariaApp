## 🤝 Contributing Guidelines for Veterinary API Project

Gracias por tu interés en contribuir al proyecto. Este repositorio sigue reglas claras para garantizar un desarrollo colaborativo limpio, ordenado y mantenible.

---

### ✅ Convención de nombres de ramas

Usar la siguiente estructura:

```
<tipo>/<nombre-descriptivo-en-kebab-case>
```

**Tipos permitidos:**
- `feature/` – nueva funcionalidad
- `bugfix/` – corrección de errores
- `refactor/` – mejora del código sin cambiar comportamiento
- `docs/` – documentación
- `chore/` – tareas menores (configuración, etc.)

**Ejemplos:**
- `feature/crear-endpoint-citas`
- `bugfix/error-validacion-campos`
- `docs/actualizar-readme`

---

### 📦 Commits semánticos

Usar el siguiente formato:

```
<tipo>: descripción breve del cambio
```

**Tipos comunes:**
- `feat:` Nueva funcionalidad
- `fix:` Corrección de errores
- `refactor:` Refactorización
- `docs:` Documentación
- `test:` Pruebas
- `chore:` Cambios menores o de configuración

**Ejemplo:**
```
feat: agregar validación para fechas de cita
```

---

### 📥 Pull Requests (PR)

1. Crear PR desde una rama con nombre válido hacia `main`.
2. Agregar descripción del cambio.
3. Asignar a tu compañero para revisión.
4. Relacionar PR con Issue si aplica (ej: `Closes #5`).
5. Hacer squash merge cuando sea aprobado.

---

### 🔒 Reglas de protección de rama `main`

- No se permite hacer `push` directo.
- Todo cambio debe hacerse mediante Pull Request.
- Requiere al menos 1 revisión de código antes del merge.
- Se debe usar "Squash and merge" para mantener historial limpio.

---

### 📝 Issues y Project Board

- Crear un Issue por cada tarea (funcionalidad, bug, mejora).
- Relacionar commits y PRs con el número de Issue.
- Usar la vista Kanban de GitHub Projects para seguimiento.

---

### 🔧 Automatizaciones sugeridas

- Agregar GitHub Actions para ejecutar tests o linters.
- Configurar `.editorconfig` para estilo consistente.
- Usar `.gitignore` para excluir carpetas innecesarias como `/target` o `.idea/`.

---

Gracias por seguir estas reglas y ayudar a mantener la calidad del proyecto 🙌
