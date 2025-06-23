PDF Annotation App
Overview
A web app to view PDFs, select text, annotate with color and hyperlinks, and download the modified PDF. Uses pdf.js for frontend and Spring Boot with PDFBox for backend.
Prerequisites

Java 17
Maven
Node.js (optional, for frontend server)
Modern web browser

Setup

Backend:

Go to backend/
Run mvn clean install
Start server: mvn spring-boot:run
Runs on http://localhost:8080


Frontend:

Place pdfjs-dist in frontend/ (from https://mozilla.github.io/pdf.js/)
Serve frontend/ (e.g., npx http-server)
Access at http://localhost:8080


Sample PDF:

Use sample.pdf in root.



Usage

Upload PDF or use sample.pdf.
Select text.
Pick color, enter URL.
Click "Apply" to annotate.
Click "Download" for modified PDF.

Assumptions

Single-page PDFs.
Font size 12pt, Helvetica.
In-memory annotations.

Limitations

No multi-page support.
Basic font handling.
No persistent storage.

Future Work

Add pagination.
Improve font detection.
Add session management.
