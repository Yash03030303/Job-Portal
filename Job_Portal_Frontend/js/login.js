document.getElementById("loginForm").addEventListener("submit", function (e) {
  e.preventDefault();
  verify();
});

function verify() {
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;
  fetch("http://localhost:3000/users")
    .then(res => res.json())
    .then(users => {
      const user = users.find(u => u.email === email && u.password === password);
      if (user) {
        sessionStorage.setItem("userId", user.userId);
        sessionStorage.setItem("userName", user.name);
        sessionStorage.setItem("userType", user.userType);
        if (user.userType === "ADMIN") {
          window.location.href = "company.html";
        } else {
          window.location.href = "candidate.html";
        }
      } else {
        document.getElementById("error").textContent = "Invalid email or password.";
      }
    })
    .catch(err => {
      console.error("Error fetching users:", err);
    });
}

function getUserId() {
  return sessionStorage.getItem("userId");
}

function getUserName() {
  return sessionStorage.getItem("userName");
}

function getUserType() {
  return sessionStorage.getItem("userType");
}

function logout() {
  sessionStorage.clear();
  sessionStorage.setItem("loggedOut", "true");
  window.location.href = "login.html";
}
