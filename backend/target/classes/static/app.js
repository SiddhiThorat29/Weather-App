// Fixed backend URL (same origin)
const BASE_URL = "http://localhost:8080";
const apiBase = BASE_URL + "/api";

console.log("Using backend:", apiBase);

const form = document.getElementById('searchForm');
const cityInput = document.getElementById('cityInput');
const resultDiv = document.getElementById('result');
const errDiv = document.getElementById('err');
const emptyState = document.getElementById('empty');

function showError(text) {
  errDiv.textContent = text || '';
}

function showEmpty(show) {
  if (!emptyState) return;
  emptyState.style.display = show ? 'block' : 'none';
}

function showResultCard(show) {
  if (!resultDiv) return;
  if (show) {
    resultDiv.classList.remove('hidden');
  } else {
    resultDiv.classList.add('hidden');
  }
}

form && form.addEventListener('submit', async (e) => {
  e.preventDefault();
  showError('');
  showResultCard(false);
  showEmpty(false);

  const city = (cityInput?.value || '').trim();
  if (!city) {
    showError('Enter a city name');
    showEmpty(true);
    return;
  }

  try {
    const url = `${apiBase}/weather?city=${encodeURIComponent(city)}`;
    console.log("Request:", url);

    const res = await fetch(url);

    if (!res.ok) {
      const body = await res.json().catch(() => ({ error: "Unknown error" }));
      showError(body.error || `Error ${res.status}`);
      showEmpty(true);
      return;
    }

    const json = await res.json();
    renderResult(json);

  } catch (err) {
    console.error("Fetch failed:", err);
    showError(err.message || "Failed to fetch");
    showEmpty(true);
  }
});

function renderResult(data) {
  const temp = data.temperature?.temp ?? '—';
  const feels = data.temperature?.feels_like ?? '—';
  const desc = String(data.weather?.description ?? '');
  const iconCode = data.weather?.icon ?? '';
  const iconUrl = iconCode ? `https://openweathermap.org/img/wn/${iconCode}@2x.png` : '';

  const humidity = data.temperature?.humidity ?? '—';
  const pressure = data.temperature?.pressure ?? '—';
  const wind = data.wind?.speed ?? '—';
  const visibility = data.visibility ?? '—';
  const coordsLat = data.coords?.lat ?? '—';
  const coordsLon = data.coords?.lon ?? '—';

  const html = `
    <div class="result-card">
      <div class="left-pane">
        <div class="icon-wrap">${ iconUrl ? `<img src="${iconUrl}" alt="icon" />` : '<div style="width:90px;height:90px;border-radius:12px;background:linear-gradient(135deg,#fff8e6,#fffdf6)"></div>'}</div>
        <div class="temp">${temp}°C</div>
        <div class="feels">Feels like ${feels}°C</div>
      </div>

      <div class="right-pane">
        <div class="city">${data.city ?? ''}${data.country ? ', ' + data.country : ''}</div>
        <div class="meta-row">
          <div class="meta-item">🌡️ Weather Condition - ${desc}</div>
          <div class="meta-item">💧 Humidity - ${humidity}%</div>
          <div class="meta-item">🧭 Wind - ${wind} m/s</div>
          <div class="meta-item">👁️ Visibility - ${visibility}</div>
        </div>

        <div class="stats" style="margin-top:10px;">
          <div class="stat">Pressure: ${pressure} hPa</div>
          <div class="stat">Coords: ${coordsLat}, ${coordsLon}</div>
          <div class="stat">Timezone: ${data.timezone ?? '—'}</div>
        </div>
      </div>
    </div>
  `;

  resultDiv.innerHTML = html;
  showResultCard(true);
  showEmpty(false);
  showError('');
}
