let pdfDoc = null;

pdfjsLib.getDocument('../sample.pdf').then((pdf) => {
    pdfDoc = pdf;
    renderPage(1);
}).catch((error) => {
    alert('Error loading PDF: ' + error.message);
});

async function renderPage(pageNum) {
    const page = await pdfDoc.getPage(pageNum);
    const viewport = page.getViewport({ scale: 1.5 });
    const canvas = document.getElementById('pdf-canvas');
    canvas.height = viewport.height;
    canvas.width = viewport.width;
    const ctx = canvas.getContext('2d');
    await page.render({ canvasContext: ctx, viewport }).promise;

    canvas.addEventListener('mouseup', handleTextSelection);
}

function handleTextSelection() {
    const selection = window.getSelection();
    if (selection.toString()) {
        const range = selection.getRangeAt(0);
        const rect = range.getBoundingClientRect();
        const pageRect = document.getElementById('pdf-canvas').getBoundingClientRect();
        window.currentAnnotation = {
            text: selection.toString(),
            page: 1,
            x: rect.left - pageRect.left,
            y: rect.top - pageRect.top,
            width: rect.width,
            height: rect.height,
            color: document.getElementById('color-picker').value,
            link: document.getElementById('link-input').value
        };
    }
}

async function applyAnnotation() {
    if (!window.currentAnnotation) {
        alert('No text selected.');
        return;
    }
    const file = document.getElementById('pdf-upload').files[0] || await fetch('../sample.pdf').then(res => res.blob());
    const response = await fetch('/api/annotate', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            annotations: [window.currentAnnotation],
            originalPdf: await fileToBase64(file)
        })
    });
    if (!response.ok) {
        alert('Failed to apply annotation.');
        return;
    }
    const blob = await response.blob();
    const url = URL.createObjectURL(blob);
    pdfDoc = await pdfjsLib.getDocument(url).promise;
    renderPage(1);
}

async function downloadAnnotated() {
    const response = await fetch('/api/annotated');
    if (!response.ok) {
        alert('Failed to download PDF.');
        return;
    }
    const blob = await response.blob();
    const link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download = 'annotated.pdf';
    link.click();
}

function fileToBase64(file) {
    return new Promise((resolve) => {
        const reader = new FileReader();
        reader.onload = () => resolve(reader.result);
        reader.readAsDataURL(file);
    });
}