# Desmontando Java Swing en Profundidad

Java Swing es un framework de interfaces gráficas construido completamente en Java, basado sobre AWT. Este documento desglosa su funcionamiento interno, desde la creación de ventanas hasta el sistema de eventos y renderizado.

---

## 📐 Índice

1. [Arquitectura general](#1-arquitectura-de-java-swing-visión-general)  
2. [Creación de ventanas (`JFrame`, etc.)](#2-creación-de-ventanas-top-level-containers)  
3. [Event Dispatch Thread (EDT)](#3-el-event-dispatch-thread-edt)  
4. [Jerarquía de componentes](#4-sistema-de-componentes-y-jerarquía)  
5. [Renderizado y Repaint](#5-dibujo-y-repaint)  
6. [Layout y redibujo](#6-layout-y-redibujo)  
7. [Sistema de Look and Feel](#7-look-and-feel-lf)  
8. [Resumen por capas](#8-resumen-de-capas-internas)  

---

## 1. Arquitectura de Java Swing: Visión General

- **Swing** es parte de Java Foundation Classes (JFC).  
- Construido sobre **AWT**, pero sin depender de componentes nativos.  
- Es **lightweight** (completamente Java).  
- Utiliza el patrón MVC internamente.  

---

## 2. Creación de Ventanas (Top-Level Containers)

### Contenedores top-level:

- `JFrame`  
- `JDialog`  
- `JApplet` (obsoleto)  

### Internamente:

1. `JFrame` hereda de `Frame` (AWT) y crea un "peer" nativo (ventana OS).  
2. Swing inyecta un `JRootPane` que contiene:  
   - `GlassPane`  
   - `LayeredPane`  
   - `ContentPane`  
   - Opcionalmente `JMenuBar`  

### Ejemplo:

```java
JFrame frame = new JFrame("Mi ventana");
frame.setSize(400, 300);
frame.setVisible(true);
```

---

## 3. El Event Dispatch Thread (EDT)

- Swing es **single-threaded**.  
- Todo el código de UI debe ejecutarse en el **EDT**.  

### Internamente:

1. Eventos se encolan en `EventQueue`.  
2. `EventQueue` los despacha en el EDT.  
3. Listeners como `ActionListener`, `MouseListener`, etc. se notifican.  

### Código seguro:

```java
SwingUtilities.invokeLater(() -> {
    // Crear UI aquí
});
```

---

## 4. Sistema de Componentes y Jerarquía

```plaintext
Component (AWT)
└── Container (AWT)
    └── JComponent (Swing)
        ├── JPanel
        ├── JButton
        ├── JLabel
        └── ...
```

Cada `JComponent` incluye:

- Propiedades visuales (color, borde, opacidad).  
- `ComponentUI` delegado (para L&F).  
- Métodos `paintComponent`, `paintBorder`, etc.  

---

## 5. Dibujo y Repaint

### Flujo:

1. Llamada a `repaint()`.  
2. `RepaintManager` registra una región sucia.  
3. Se coloca un `PaintEvent` en la `EventQueue`.  
4. Swing llama:  
   - `paint(Graphics g)`  
     - `paintComponent(g)`  
     - `paintBorder(g)`  
     - `paintChildren(g)`  

### Ejemplo:

```java
@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.RED);
    g.drawString("Hola Mundo", 10, 20);
}
```

### Doble Buffer:

- Gestionado por `RepaintManager`.  
- Evita parpadeo (flickering).  
- Buffer se dibuja completamente antes de mostrarse.  

---

## 6. Layout y Redibujo

Swing usa **Layout Managers**:

- `BorderLayout`, `FlowLayout`, `GridBagLayout`, etc.  

### Flujo de cambio de layout:

1. Se llama `invalidate()`.  
2. Swing marca componente como sucio.  
3. Se propaga la invalidación.  
4. Luego se llama a `validate()` → `doLayout()`.  
5. Finalmente se redibuja (`repaint()`).  

---

## 7. Look and Feel (L&F)

Swing permite cambiar el estilo visual sin cambiar la lógica.

### Ejemplo:

```java
UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
```

### Internamente:

- Cada `JComponent` tiene un `ComponentUI` (delegado de dibujo).  
- Este define cómo se pinta y cómo reacciona el componente.  

---

## 8. Resumen de Capas Internas

| Capa           | Descripción                                       |
| -------------- | ------------------------------------------------- |
| JVM / OS       | Ventanas nativas vía AWT peers                    |
| AWT            | Gestiona contenedores nativos (`Frame`, `Canvas`) |
| Swing          | Componentes en Java (`JComponent`, etc.)          |
| EventQueue     | Cola de eventos procesada por el EDT              |
| RepaintManager | Coordina `repaint`, buffers y regiones sucias     |
| ComponentUI    | Dibujo de componentes según L&F                   |
| Graphics2D     | API de dibujo vectorial, texto y formas           |

---

## 📌 Herramientas útiles para analizar Swing

- `VisualVM` con plugin de UI (monitoreo de eventos, repaint).  
- `jconsole` para ver uso de hilos y memoria.  
- Logging personalizado de eventos y repaints.  

---

## 🧭 ¿Qué sigue?

Puedes explorar más a fondo cada una de estas capas:

- `RepaintManager` y cómo gestiona buffers  
- El flujo de eventos (`EventQueue`)  
- Layout dinámico (`invalidate`, `validate`)  
- ComponentUI y personalización de Look & Feel  

Pide cualquiera y te lo detallo con ejemplos y referencias internas del JDK.
