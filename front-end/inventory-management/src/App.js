import React, { useState, useEffect } from 'react';
import logo from './logo.svg';
import './App.css';


function App() {
    const [inventoryData, setInventoryData] = useState(null);

    // Function to handle GET request
      useEffect(() => {
        const fetchData = async () => {
          try {
            const response = await fetch('http://localhost:8080/inventory/count'); // Replace with your Java API GET endpoint
            if (!response.ok) {
              throw new Error('Network response was not ok');
            }
            const data = await response.json();
            setInventoryData(data);
          } catch (error) {
            console.error('Error fetching data:', error);
          }
        };

        fetchData();
      }, []);

      // Function to handle POST request (example)
        const handlePost = async () => {
          try {
            const response = await fetch('http://localhost:8080/inventory/decrement', {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json',
              },
            });
            if (!response.ok) {
              throw new Error('Network response was not ok');
            }
            // Handle response from POST if needed
          } catch (error) {
            console.error('Error posting data:', error);
          }
        };

  return (
    <div className="App">
      <header className="App-header">
        <h2>Inventory Count</h2>
                {inventoryData ? (
                  <div>
                    <pre>{JSON.stringify(inventoryData)}</pre>
                    //{/* Render other parts of the fetched data as needed */}
                  </div>
                ) : (
                  <p>Loading inventory data...</p>
                )}
                <button onClick={handlePost}>Buy Now</button>
      </header>
    </div>
  );
}

export default App;
