import React, { useState, useEffect } from 'react';
//import logo from './logo.svg';
import './App.css';


function App() {

    const [inventoryData, setInventoryData] = useState(null);

    useEffect(() => {
      console.log('Fetching data...');
        fetchData();
    }, []);

    // Function to handle GET request
    const fetchData = async () => {
                try {
                  const response = await fetch('http://localhost:8080/inventory/count');
                  if (!response.ok) {
                    throw new Error('Network response was not ok');
                  }
                  const data = await response.json();
                  setInventoryData(data);
                } catch (error) {
                  console.error('Error fetching data:', error);
                }
              };

    // Function to handle POST request
    const handlePost = async () => {
          try {
            const response = await fetch('http://localhost:8080/inventory/decrement', {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json',
              },
              body: {}
            });
            if (!response.ok) {
              throw new Error('Network response was not ok');
            }
            fetchData();
          } catch (error) {
            console.error('Error posting data:', error);
          }
        };


  return (
    <div className="App">
      <header className="App-header">
        <h2>Inventory Count</h2>
        <div>
              {inventoryData ? (
                <div>
                  <h2>{JSON.stringify(inventoryData)}</h2>
                </div>
              ) : (
                <p>Loading...</p>
              )}
         </div>
        <button onClick={handlePost}>Buy Now</button>
      </header>
    </div>
  );
}

export default App;
