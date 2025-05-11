export function initSearch() {
  const inputs = [
    document.getElementById('search-first'),
    document.getElementById('search-second'),
  ];
  const results = document.getElementById('search-results');
  const clearButton = document.getElementById('clear-button');

  const handleSearch = async (e) => {
    const input = e.target;
    const query = input.value;

    if (query.length < 2) {
      results.innerHTML = '';
      return;
    }

    try {
      const url =
        'https://en.wikipedia.org/w/api.php?action=query&generator=prefixsearch&format=json&origin=\*&prop=pageprops|pageimages|description&redirects=&ppprop=displaytitle&piprop=thumbnail&pithumbsize=120&pilimit=6&gpsnamespace=0&gpslimit=6';
      const res = await fetch(`${url}&gpssearch=${encodeURIComponent(query)}`);
      const data = await res.json();

      const html = Object.values(data.query.pages)
        .map((result) => {
          const page = data.query.pages[result.pageid];
          return `
                <div class="result">
                    <h3>${page.title}</h3>
                    <img src="${page.thumbnail ? page.thumbnail.source : ''}" alt="${page.title}">
                    <p id="result-description" data-pageid="${page.pageid}">${page.description || 'No description available'}</p>
                </div>
                            `;
        })
        .join('');

      results.innerHTML = html;
      clearButton.style.display = 'block';

      const buttons = document.querySelectorAll('.result').forEach((button) => {
        button.addEventListener('click', () => {
          const title = button.querySelector('h3').innerText;
          const image = button.querySelector('img').src;
          const description = button.querySelector('#result-description').innerText;
          const pageid = button.querySelector('#result-description').dataset.pageid;

          input.value = title;
          input.setAttribute('data-pageid', pageid);

          document.querySelectorAll('.result').forEach((result) => {
            result.classList.remove('selected');
          });
          button.classList.add('selected');
        });
      });
    } catch (error) {
      console.error('Error fetching data from Wikipedia:', error);
    }
  };
  inputs.forEach((input) => input.addEventListener('input', handleSearch));
  clearButton.addEventListener('click', () => {
    results.innerHTML = '';
    clearButton.style.display = 'none';
  });
};
