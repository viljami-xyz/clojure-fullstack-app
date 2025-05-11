export function initTask() {
  async function pageSummary(id) {
    const summary = await fetch(`/wiki-by-id?id=${id}`).then((res) => res.json());
    return summary;
  }
  async function displaySummary(summary, container_id) {
    const container = document.getElementById(container_id);
    const html = `
      <div class="summary" id="summary-${container_id}">
        <h3>${summary.title}</h3>
        <img src="${summary.picture}" alt="${summary.title}">
        <p>${summary.description}</p>
      </div>
    `;
    container.innerHTML = html;
  }
  const results = document.getElementById('search-results');
  const search_button = document.getElementById('search-button');
  if (!search_button) {
    console.error('Search button not found');
    return;
  }
  const start_search_task = async (e) => {
      results.innerHTML = '';
    const first_page_id = document
      .getElementById('search-first')
      .getAttribute('data-pageid');
    const second_page_id = document
      .getElementById('search-second')
      .getAttribute('data-pageid');

    const first_summary = await pageSummary(first_page_id);
    const second_summary = await pageSummary(second_page_id);

      await displaySummary(first_summary, 'result-first');
      await displaySummary(second_summary, 'result-second');


  };

  search_button.addEventListener('click', start_search_task);
}
