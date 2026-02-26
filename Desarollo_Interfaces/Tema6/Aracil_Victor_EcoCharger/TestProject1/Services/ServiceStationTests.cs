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
    public class ServiceStationTests
    {
        // TEST LISTAR
        [TestMethod]
        public async Task Test_Listar()
        {
            var service = new ServiceStation();

            var lista = await service.Listar();

            Assert.IsNotNull(lista);
        }

        // TEST INSERTAR
        [TestMethod]
        public async Task Test_Insertar()
        {
            var service = new ServiceStation();

            var station = new Station
            {
                Name = "STATION_TEST",
                Address = "Direccion Test",
                Latitude = 10,
                Longitude = 20,
                IsActive = true
            };

            var result = await service.Insertar(station);

            Assert.IsTrue(result.Id > 0);

            await service.Borrar(result.Id);
        }

        // TEST INSERTAR NULL
        [TestMethod]
        public async Task Test_Insertar_Null()
        {
            var service = new ServiceStation();

            await Assert.ThrowsExceptionAsync<ArgumentNullException>(
                () => service.Insertar(null));
        }

        // TEST ACTUALIZAR EXISTENTE
        [TestMethod]
        public async Task Test_Actualizar_Existente()
        {
            var service = new ServiceStation();

            var station = new Station
            {
                Name = "STATION_UPDATE",
                Address = "Direccion Test",
                Latitude = 0,
                Longitude = 0,
                IsActive = true
            };

            var inserted = await service.Insertar(station);

            inserted.Name = "STATION_UPDATE_EDITED";

            var result = await service.Actualizar(inserted);

            Assert.IsTrue(result);

            await service.Borrar(inserted.Id);
        }

        // TEST ACTUALIZAR INEXISTENTE
        [TestMethod]
        public async Task Test_Actualizar_Inexistente()
        {
            var service = new ServiceStation();

            var fake = new Station
            {
                Id = -9999,
                Name = "Fake",
                Address = "Fake",
                Latitude = 0,
                Longitude = 0,
                IsActive = true
            };

            var result = await service.Actualizar(fake);

            Assert.IsFalse(result);
        }

        // TEST BORRAR EXISTENTE
        [TestMethod]
        public async Task Test_Borrar()
        {
            var service = new ServiceStation();

            var station = new Station
            {
                Name = "STATION_DELETE",
                Address = "Direccion Test",
                Latitude = 0,
                Longitude = 0,
                IsActive = true
            };

            var inserted = await service.Insertar(station);

            var result = await service.Borrar(inserted.Id);

            Assert.IsTrue(result);
        }

        // TEST BORRAR INEXISTENTE
        [TestMethod]
        public async Task Test_Borrar_Inexistente()
        {
            var service = new ServiceStation();

            var result = await service.Borrar(-9999);

            Assert.IsFalse(result);
        }
    }
}
