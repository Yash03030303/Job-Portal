const apiUrl = "http://localhost:3000/job";
const form = document.getElementById("jobForm");
const nameInput = document.getElementById("name");
const industryInput = document.getElementById("industry");
const locationInput = document.getElementById("location");
const submitBtn = document.getElementById("submitBtn");
const searchInput = document.createElement("input");
let editId = null;
let allJobs = [];
let sortDirection = 1;

// Search bar setup
searchInput.setAttribute("type", "text");
searchInput.setAttribute("placeholder", "Search by Job Name...");
searchInput.classList.add("form-control", "mb-3");
document.querySelector(".table").parentNode.insertBefore(searchInput, document.querySelector(".table"));

searchInput.addEventListener("input", () => {
    const searchTerm = searchInput.value.toLowerCase();
    const filtered = allJobs.filter(cat =>
        cat.name.toLowerCase().includes(searchTerm)
    );
    renderJobs(filtered);
});

// Validation
[nameInput, industryInput].forEach(input => {
    input.addEventListener("blur", () => {
        if (input.value.trim().length < 5) {
            input.classList.add("is-invalid");
            input.classList.remove("is-valid");
        } else {
            input.classList.remove("is-invalid");
            input.classList.add("is-valid");
        }
    });
});

function validateForm() {
    let isValid = true;
    [nameInput, industryInput].forEach(input => {
        if (input.value.trim().length < 5) {
            input.classList.add("is-invalid");
            input.classList.remove("is-valid");
            isValid = false;
        } else {
            input.classList.remove("is-invalid");
            input.classList.add("is-valid");
        }
    });
    return isValid;
}

form.addEventListener("submit", function (e) {
    e.preventDefault();
    if (!validateForm()) return;

    const job = {
        name: nameInput.value.trim(),
        industry: industryInput.value.trim(),
        location: locationInput.value.trim()
    };

    if (editId) {
        fetch(`${apiUrl}/${editId}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(job)
        }).then(() => {
            fetchAndRenderJobs();
            form.reset();
            editId = null;
            submitBtn.textContent = "Add Job";
            clearValidation();
        });
    } else {
        fetch(apiUrl, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(job)
        }).then(() => {
            fetchAndRenderJobs();
            form.reset();
            clearValidation();
        });
    }
});

function clearValidation() {
    nameInput.classList.remove("is-valid", "is-invalid");
    industryInput.classList.remove("is-valid", "is-invalid");
}

function fetchAndRenderJobs() { //OG
    fetch(apiUrl)
        .then(res => res.json())
        .then(data => {
            allJobs = data;
            renderJobs(data);
        });
}

function renderJobs(jobs) {     //OG
    const tbody = document.getElementById("jobTableBody");
    tbody.innerHTML = "";

    jobs.forEach(cat => {
        const row = document.createElement("tr");
        row.innerHTML = `
      
      <td>${cat.name}</td>
      <td>${cat.industry}</td>
      <td>${cat.location}</td>
      <td>
        <button class="btn btn-sm btn-outline-primary me-1 edit-btn" data-id="${cat.id}">
          <i class="bi bi-pencil-square"></i>
        </button>
        <button class="btn btn-sm btn-outline-danger delete-btn" data-id="${cat.id}">
          <i class="bi bi-trash"></i>
        </button>
      </td>
    `;
        tbody.appendChild(row);
    });

    document.querySelectorAll(".edit-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            const id = btn.getAttribute("data-id");
            loadJobForEdit(id);
        });
    });

    document.querySelectorAll(".delete-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            const id = btn.getAttribute("data-id");
            deleteJob(id);
        });
    });
}

// Sorting logic on Job Name
document.querySelector("th:nth-child(1)").style.cursor = "pointer";
document.querySelector("th:nth-child(3)").style.cursor = "pointer";
document.querySelector("th:nth-child(1)").addEventListener("click", () => {
    const sorted = [...allJobs].sort((a, b) =>
        a.name.localeCompare(b.name) * sortDirection
    );
    const sortedlo = [...allJobs].sort((a, b) =>
        a.location.localeCompare(b.location) * sortDirection
    );
    sortDirection *= -1;
    renderJobs(sorted);
    renderJobs(sortedlo);
});

// Sorting logic on Job Location
document.querySelector("th:nth-child(3)").addEventListener("click", () => {
    const sorted = [...allJobs].sort((a, b) =>
        a.location.localeCompare(b.location) * sortDirection
    );
    sortDirection *= -1;
    renderJobs(sorted);
});

function loadJobForEdit(id) {
    fetch(`${apiUrl}/${id}`)
        .then(res => res.json())
        .then(data => {
            nameInput.value = data.name;
            industryInput.value = data.industry;
            locationInput.value = data.location;
            editId = id;
            submitBtn.textContent = "Update Job";
        });
}

function deleteJob(id) {
    if (confirm("Are you sure you want to delete this Job?")) {
        fetch(`${apiUrl}/${id}`, {
            method: "DELETE"
        }).then(() => fetchAndRenderJobs());
    }
}


fetchAndRenderJobs();





