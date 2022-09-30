/**
 * @author Markitanov Vadim
 * @since 20.09.2022
 */
console.log('Start content script.');

const button = document.getElementById("test-button");

if (button != null) {
    button.addEventListener("click", () =>
            alert("Hello!")
        , false);
}

