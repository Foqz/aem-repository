var factCategory = document.getElementById("factCategory");
var randomFactCheckbox = document.getElementById("randomFactCheckbox");
var factTextInput = document.getElementById("factTextInput");
var factCardTitle = document.getElementById("factCardTitle");
var factValue = document.getElementById("factValue");
var factUrl = document.getElementById("factUrl");
var generateButton = document.getElementById("generateButton");

$(function () {
    $('#randomFactCheckbox').change(function () {
        if (this.checked) {
            if (generateButton.classList.contains("disabled")) {
                generateButton.classList.remove("disabled");
            }
            if (factCategory !== null) {
                factCategory.value = factCategory.options[0].value
            }
            if (factTextInput !== null) {
                factTextInput.value = "";
            }
        } else if (!this.checked && ((factCategory === null && factTextInput === null) ||
            (factCategory !== null && factTextInput === null && factCategory.value === "None") ||
            (factCategory === null && factTextInput !== null && factTextInput.value === "") ||
            (factTextInput !== null && factTextInput.value === "" && factCategory !== null && factCategory.value === "None"))
        ) {
            if (!generateButton.classList.contains("disabled")) {
                generateButton.classList.add("disabled");
            }
        }
    })
});

$(function () {
    $('#factTextInput').change(function () {
        if (this.value !== "") {
            if (randomFactCheckbox.checked) {
                $('#randomFactCheckbox').bootstrapToggle('off');
            }
            if (factCategory != null) {
                factCategory.value = factCategory.options[0].value;
            }
        } else if (this.value === "") {
            $('#randomFactCheckbox').bootstrapToggle('on');
        }
    })
});

$(function () {
    $('#factCategory').change(function () {
        if (this.value !== factCategory.options[0].value) {
            if (randomFactCheckbox.checked) {
                $('#randomFactCheckbox').bootstrapToggle('off');
            }
            if (factTextInput !== null) {
                factTextInput.value = "";
            }
        } else if (this.value === factCategory.options[0].value) {
            $('#randomFactCheckbox').bootstrapToggle('on');
        }
    })
});

function handleChuckServlet() {
    if (!generateButton.classList.contains("disabled")) {
        $.ajax({
            method: "GET",
            url: this.location.origin + "/.getChuckFacts",
            dataType: "json",
            contentType: "application/json",
            data: {
                randomFactCheckbox: randomFactCheckbox.checked,
                factTextInput: factTextInput ? factTextInput.value : "",
                factCategory: factCategory ? factCategory.value === "None" ? "" : factCategory.value : ""
            }
        })
            .done(function (response) {
                if (Array.isArray(response.result)) {
                    response = response.result[Math.floor(Math.random() * response.result.length)]
                }
                factCardTitle.textContent = "Generated with ChuckNorris API!";
                factValue.textContent = response.value;
                factUrl.classList.remove("disabled");
                factUrl.href = response.url;
            })
            .fail(function () {
                factUrl.classList.add("disabled");
                factCardTitle.textContent = ";(";
                factValue.textContent = "No results for current input !"
            })
    }

}
