var factCategory = document.getElementById("factCategory");
var randomFactCheckbox = document.getElementById("randomFactCheckbox");
var factTextInput = document.getElementById("factTextInput");
var factCardTitle = document.getElementById("factCardTitle");
var factValue = document.getElementById("factValue");
var factUrl = document.getElementById("factUrl");

$(function () {
    $('#randomFactCheckbox').change(function () {
        if (this.checked) {
            if (factCategory !== null) {
                factCategory.value = factCategory.options[0].value
            }
            if (factTextInput !== null) {
                factTextInput.value = "";
            }
        }
    })
});
$(function () {
    $('#factTextInput').change(function () {
        if (this.value !== "" || this.value !== undefined) {
            if (randomFactCheckbox.checked) {
                $('#randomFactCheckbox').bootstrapToggle('off');
            }
            if (factCategory != null) {
                factCategory.value = factCategory.options[0].value;
            }
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
        }
    })
});

function handleChuckServlet() {s
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
            if (Array.isArray(response.result)){
                response = response.result[Math.floor(Math.random() * response.result.length)]
            }
            factCardTitle.textContent = "Generated with ChuckNorris API!";
            factValue.textContent = response.value;
            factUrl.classList.remove("disabled");
            factUrl.href = response.url;
        })
        .fail(function (response) {
            factUrl.classList.add("disabled");
            factCardTitle.textContent = ";(";
            factValue.textContent = "Oopss ! something went wrong"
        })
}
