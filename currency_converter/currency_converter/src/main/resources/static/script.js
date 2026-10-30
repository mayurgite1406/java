
const countryList = {
    AED: "AE", AFN: "AF", XCD: "AG", ALL: "AL",
    AMD: "AM", ANG: "CW", AOA: "AO", ARS: "AR",
    AUD: "AU", AZN: "AZ", BAM: "BA", BBD: "BB",
    BDT: "BD", BGN: "BG", BHD: "BH", BIF: "BI",
    BMD: "BM", BND: "BN", BOB: "BO", BRL: "BR",
    BSD: "BS", BWP: "BW", BYN: "BY", BZD: "BZ",
    CAD: "CA", CDF: "CD", CHF: "CH", CLP: "CL",
    CNY: "CN", COP: "CO", CRC: "CR", CUP: "CU",
    CVE: "CV", CZK: "CZ", DJF: "DJ", DKK: "DK",
    DOP: "DO", DZD: "DZ", EGP: "EG", ETB: "ET",
    EUR: "EU", FJD: "FJ", GBP: "GB", GEL: "GE",
    GHS: "GH", GIP: "GI", GMD: "GM", GNF: "GN",
    GTQ: "GT", GYD: "GY", HKD: "HK", HNL: "HN",
    HTG: "HT", HUF: "HU", IDR: "ID", ILS: "IL",
    INR: "IN", IQD: "IQ", IRR: "IR", ISK: "IS",
    JMD: "JM", JOD: "JO", JPY: "JP", KES: "KE",
    KGS: "KG", KHR: "KH", KMF: "KM", KPW: "KP",
    KRW: "KR", KWD: "KW", KYD: "KY", KZT: "KZ",
    LAK: "LA", LBP: "LB", LKR: "LK", LRD: "LR",
    LSL: "LS", LYD: "LY", MAD: "MA", MDL: "MD",
    MGA: "MG", MKD: "MK", MMK: "MM", MNT: "MN",
    MOP: "MO", MUR: "MU", MVR: "MV", MWK: "MW",
    MXN: "MX", MYR: "MY", MZN: "MZ", NAD: "NA",
    NGN: "NG", NIO: "NI", NPR: "NP", NZD: "NZ",
    OMR: "OM", PAB: "PA", PEN: "PE", PGK: "PG",
    PHP: "PH", PKR: "PK", PLN: "PL", PYG: "PY",
    QAR: "QA", RON: "RO", RSD: "RS", RUB: "RU",
    RWF: "RW", SAR: "SA", SBD: "SB", SCR: "SC",
    SDG: "SD", SEK: "SE", SGD: "SG", SOS: "SO",
    SRD: "SR", SYP: "SY", SZL: "SZ", THB: "TH",
    TJS: "TJ", TMT: "TM", TND: "TN", TOP: "TO",
    TRY: "TR", TTD: "TT", TWD: "TW", TZS: "TZ",
    UAH: "UA", UGX: "UG", USD: "US", UYU: "UY",
    UZS: "UZ", VND: "VN", VUV: "VU", YER: "YE",
    ZAR: "ZA"
};

const selectFrom = document.querySelector(".select-first");
const selectTo = document.querySelector(".select-second");
const amount = document.querySelector(".input");
const answer = document.querySelector(".answer");
const generate = document.querySelector(".generate");
const flagFrom = document.querySelector(".flag-first");
const flagTo = document.querySelector(".flag-second");
const swapButton = document.querySelector("#ex-logo");

// Update country flags
function change() {
    const from = selectFrom.value;
    const to = selectTo.value;

    if (countryList[from]) {
        flagFrom.src =
            `https://flagsapi.com/${countryList[from]}/flat/64.png`;
        flagFrom.alt = `${from} flag`;
    }

    if (countryList[to]) {
        flagTo.src =
            `https://flagsapi.com/${countryList[to]}/flat/64.png`;
        flagTo.alt = `${to} flag`;
    }
}

// Get exchange rate through Java Spring Boot
async function convert() {
    const num = Number(amount.value);
    const from = selectFrom.value;
    const to = selectTo.value;

    // Validate the entered amount
    if (amount.value.trim() === "" ||
        !Number.isFinite(num) ||
        num <= 0) {

        answer.textContent = "Please enter a valid amount.";
        return;
    }

    // Same currency requires no API request
    if (from === to) {
        answer.textContent = `${num} ${from} = ${num.toFixed(2)} ${to}`;
        return;
    }

    answer.textContent = "Getting exchange rate...";
    generate.style.pointerEvents = "none";
    generate.style.opacity = "0.7";

    try {
        const URL =
            `/api/convert?amount=${encodeURIComponent(num)}` +
            `&from=${encodeURIComponent(from)}` +
            `&to=${encodeURIComponent(to)}`;

        const response = await fetch(URL);
        const result = await response.json();

        if (!response.ok) {
            throw new Error(
                result.error || "Unable to convert currency."
            );
        }

        answer.textContent =
            `${num} ${from} = ` +
            `${Number(result.result).toLocaleString("en-US", {
                minimumFractionDigits: 2,
                maximumFractionDigits: 2
            })} ${to}`;

    } catch (error) {
        answer.textContent =
            error.message || "Something went wrong. Try again.";
    } finally {
        generate.style.pointerEvents = "auto";
        generate.style.opacity = "1";
    }
}

// Convert when Enter is pressed
function converts(event) {
    if (event.key === "Enter") {
        event.preventDefault();
        convert();
    }
}

// Swap the selected currencies
function swapCurrencies() {
    const temp = selectFrom.value;

    selectFrom.value = selectTo.value;
    selectTo.value = temp;

    change();

    // Recalculate if a valid amount has already been entered
    if (amount.value.trim() !== "") {
        convert();
    } else {
        answer.textContent = "Enter an amount to convert.";
    }
}

// Event listeners
selectFrom.addEventListener("change", change);
selectTo.addEventListener("change", change);

generate.addEventListener("click", convert);
amount.addEventListener("keydown", converts);
swapButton.addEventListener("click", swapCurrencies);

// Set the initial flags
change();
