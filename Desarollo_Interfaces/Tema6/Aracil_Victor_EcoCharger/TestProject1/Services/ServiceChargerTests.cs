using Aracil_Victor_EcoCharger.Models;
using Aracil_Victor_EcoCharger.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TestProject1.Services
{

    [TestClass]
    public class ServiceChargerTests
    {
        // TEST LISTAR
        [TestMethod]
        public async Task Test_Listar()
        {
            var service = new ServiceCharger();

            var lista = await service.Listar();

            Assert.IsNotNull(lista);
        }

        // TEST INSERTAR
        [TestMethod]
        public async Task Test_Insertar()
        {
            var stationService = new ServiceStation();
            var chargerService = new ServiceCharger();

            // Creamos estación auxiliar
            var station = await stationService.Insertar(new Station
            {
                Name = "Station_Test",
                Address = "Direccion Test",
                Latitude = 0,
                Longitude = 0,
                IsActive = true
            });

            var charger = new Charger
            {
                Type = 1,
                MaxPower = 50,
                IsOccupied = false,
                StationId = station.Id
            };

            var result = await chargerService.Insertar(charger);

            Assert.IsTrue(result.Id > 0);

            await chargerService.Borrar(result.Id);
            await stationService.Borrar(station.Id);
        }

        // TEST INSERTAR NULL
        [TestMethod]
        public async Task Test_Insertar_Null()
        {
            var service = new ServiceCharger();

            await Assert.ThrowsExceptionAsync<ArgumentNullException>(
                () => service.Insertar(null));
        }

        // TEST ACTUALIZAR EXISTENTE
        [TestMethod]
        public async Task Test_Actualizar_Existente()
        {
            var stationService = new ServiceStation();
            var chargerService = new ServiceCharger();

            var station = await stationService.Insertar(new Station
            {
                Name = "Station_Update",
                Address = "Direccion Update",
                Latitude = 1,
                Longitude = 1,
                IsActive = true
            });

            var charger = await chargerService.Insertar(new Charger
            {
                Type = 1,
                MaxPower = 60,
                IsOccupied = false,
                StationId = station.Id
            });

            charger.MaxPower = 120;

            var result = await chargerService.Actualizar(charger);

            Assert.IsTrue(result);

            await chargerService.Borrar(charger.Id);
            await stationService.Borrar(station.Id);
        }

        // TEST ACTUALIZAR INEXISTENTE
        [TestMethod]
        public async Task Test_Actualizar_Inexistente()
        {
            var service = new ServiceCharger();

            var fake = new Charger
            {
                Id = -9999,
                Type = 1,
                MaxPower = 10,
                IsOccupied = false,
                StationId = 1
            };

            var result = await service.Actualizar(fake);

            Assert.IsFalse(result);
        }

        // TEST BORRAR EXISTENTE
        [TestMethod]
        public async Task Test_Borrar()
        {
            var stationService = new ServiceStation();
            var chargerService = new ServiceCharger();

            var station = await stationService.Insertar(new Station
            {
                Name = "Station_Delete",
                Address = "Direccion Delete",
                Latitude = 2,
                Longitude = 2,
                IsActive = true
            });

            var charger = await chargerService.Insertar(new Charger
            {
                Type = 2,
                MaxPower = 80,
                IsOccupied = false,
                StationId = station.Id
            });

            var result = await chargerService.Borrar(charger.Id);

            Assert.IsTrue(result);

            await stationService.Borrar(station.Id);
        }

        // TEST BORRAR INEXISTENTE
        [TestMethod]
        public async Task Test_Borrar_Inexistente()
        {
            var service = new ServiceCharger();

            var result = await service.Borrar(-9999);

            Assert.IsFalse(result);
        }
    }
}
