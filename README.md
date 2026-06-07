# CargoShipmentTracker - GUI

**Modern Java Swing Graphical User Interface for Professional Cargo Shipment Tracking**

This project extends the original console-based Cargo Shipment Tracker (EE1004 OOP academic project) with a complete, production-ready, website-inspired desktop GUI. The interface emphasizes usability, visual clarity, real-time feedback, and clean architecture while preserving every original OOP principle and functional requirement.

## Key Features

- **Dashboard Overview**: KPI cards (total shipments, revenue, insurance liability, pending/in-transit), quick actions, and recent shipments list.
- **Intuitive Registration**: Modern form with live cost/insurance preview, dynamic weight limit validation, and instant feedback.
- **Interactive Shipments Table**: Sortable/filterable JTable with color-coded status badges (amber/blue/green/red), search, view details, and status advancement dialogs.
- **Reports & Analytics**: Summary statistics and type-wise insurance breakdown.
- **Professional UX**: Deep navy/teal color scheme, hover effects, consistent spacing, menu bar with save/load/export, status bar.
- **Data Persistence**: Built-in serialization support (Save/Load session via File menu).
- **CSV Export**: One-click export of all shipment data.
- **State Machine Enforcement**: All status transitions validated exactly as in the original enum logic.
- **No External Dependencies**: Pure Java SE + Swing.

## Project Structure

```
CargoShipmentTracker-GUI/
├── README.md
├── README.txt
├── src/main/java/com/cargotracker/
│   ├── model/                  # Original + enhanced model classes (packaged)
│   │   ├── Shipment.java (abstract + Serializable)
│   │   ├── StandardShipment.java / ExpressShipment.java / SameDayShipment.java
│   │   ├── Insurable.java
│   │   ├── ShipmentStatus.java (enum with canGoTo)
│   │   ├── InvalidStatusTransitionException.java
│   │   └── CargoCompany.java (with getAllShipments, save/load, GUI helpers)
│   └── gui/
│       ├── CargoTrackerGUI.java (launcher with demo data)
│       ├── MainDashboardFrame.java (header + sidebar nav + CardLayout)
│       └── panels/
│           ├── DashboardPanel.java
│           ├── RegisterPanel.java (live validation & preview)
│           ├── ShipmentsPanel.java (JTable + filter + export + dialogs)
│           ├── ReportsPanel.java
│           ├── ShipmentDetailDialog.java
│           └── StatusUpdateDialog.java
├── docs/
│   ├── uml/
│   │   ├── CargoShipmentTracker-ClassDiagram.puml
│   │   └── GUI-Architecture.puml
│   └── pseudocode/
│       └── ExecutionAlgorithm.md
├── Documents/                  # Original academic report (PDF)
└── CargoShipmentTracker-GUI.zip (ready for GitHub upload)
```

## How to Build and Run

### Using IDE (Recommended)
1. Open the `src/main/java` folder as a project in IntelliJ IDEA, Eclipse, or VS Code with Java extensions.
2. Run `com.cargotracker.gui.CargoTrackerGUI` as the main class.

### Command Line
```bash
cd CargoShipmentTracker-GUI
javac -d out $(find src -name "*.java")
java -cp out com.cargotracker.gui.CargoTrackerGUI
```

The application launches with 4 pre-loaded demo shipments for immediate interaction.

## Architecture Highlights

- **Model-View-Controller style**: `CargoCompany` remains the single source of truth. GUI panels observe and mutate it.
- **Strategy Pattern**: Original `Comparator` constants still available.
- **State Machine**: Fully enforced via `ShipmentStatus.canGoTo(...)`.
- **Custom Rendering**: Status badges with semantic colors in the table.
- **Live Computation**: Cost and insurance preview update as you type.


## PlantUML Diagrams

See `docs/uml/` for source `.puml` files. Render with:
- PlantUML online (plantuml.com/plantuml)
- Or local: `java -jar plantuml.jar *.puml`

## Future Enhancements (Optional)

- Full reactive updates via PropertyChangeSupport or observer pattern.
- Dark mode toggle.
- Integration with FlatLaf for even more modern L&F.
- Barcode/QR simulation for shipments.
- Multi-user or database backend.
- Export/Import capabilities.
- Map API Integration
- Advanced Data Validation & Duplication Prevention
- Notification Module

## Academic & Portfolio Value

This GUI demonstrates:
- Advanced Swing (JTable models, custom renderers, CardLayout, dialogs, menu bars)
- Event-driven programming and validation
- Clean separation of concerns
- Professional UI/UX design principles
- Backward compatibility with original console logic
- Strict business rules implementation
- Solid principles application

Full design discussion, UML class diagram, testing tables, and references are in
the [academic report](documents/EE1004_Group14_CargoShipmentTracker_JavaGUI-LaTeX_Report.pdf).

---

**Original Console Application**: Preserved in spirit and logic. The GUI is a superior presentation layer.

## Screenshots

### About Cargo Shipment Tracker
![About CargoTrack Pro](documents/screenshots/About-CargoShipmentTracker.png)

### Dashboard Overview
![Dashboard Overview](documents/screenshots/DashboardOverview.png)

### Manage Shipments
![Manage Shipments](documents/screenshots/ManageShipments.png)

### Register New Shipment
![Register New Shipment](documents/screenshots/RegisterNewShipments.png)

### Reports & Analytics
![Reports & Analytics](documents/screenshots/Reports&Analytics.png)

## Member Contributions – GUI Edition

| Member | Primary Contributions |
| :--- | :--- |
| **Atabey Aydı** (150718503) | GUI architecture & full implementation (`MainDashboardFrame`, all panels & dialogs, live validation, custom `StatusCellRenderer`, persistence, PlantUML diagrams, report integration, data-exchange design between model and GUI) |
| **Mehmet Açar** (150719020) | Original `Shipment` abstract base class, static ID counter, `advanceStatus`, specification `toString` format |
| **İsmail Hanifi Nal** (150719025) | `Insurable` interface + `StandardShipment`, `ExpressShipment`, `SaneDayShipment` with rates, caps and insurance percentages |
| **Abdulkadir Köroğlu** (150719695) | `CargoCompany` core (`registerShipment` weight guard, `HashMap` lookup, totals format) |
| **Burak Gökmen** (150720010) | Three `Comparator` strategies, `listSortedBy`, revenue & insurance summary breakdown |
| **Alperen Tufan Pelit** (150720012) | Original console `Main` menu loop, input validation, transcript capture and testing  support |

---

Each member committed from their own GitHub account; see the repository commit history for the full attribution.

**Supervision.** Course Lecturer: Assoc. Prof. Dr. Salih Bayar. Laboratory Assistant: Res. Asst. Salih Çolakoğlu.

---

## Academic context

This repository is the source-code deliverable for **Project 14 (Cargo Shipment Tracker)** of EE1004 Object-Oriented Programming at Marmara University, Spring 2025–2026 semester. The accompanying technical report (PDF) is submitted via Google Classroom and follows the Marmara University Institute of Pure and Applied Sciences thesis / Faculty of Engineering graduation-project format.

**Academic integrity.** All code in this repository is the original work of Group 14. External references (Java SE 11 documentation, Oracle tutorials, course slides) are cited in IEEE style in the report's References section. Any AI-assisted authoring was disclosed in the project report as required by the course policy.

---

## License

Released for academic evaluation as part of EE1004 (Marmara University). Re-use is permitted for educational reference with attribution to Group 14.

