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
    public class ServiceTariffTests
    {
        // TEST LISTAR
        [TestMethod]
        public async Task Test_Listar()
        {
            var service = new ServiceTariff();

            var lista = await service.Listar();

            Assert.IsNotNull(lista);
        }

        // TEST INSERTAR
        [TestMethod]
        public async Task Test_Insertar()
        {
            var service = new ServiceTariff();

            var tariff = new Tariff
            {
                Name = "TARIFA_TEST",
                PricePerKWh = 0.25m,
                StartHour = new TimeSpan(0, 0, 0),
                EndHour = new TimeSpan(23, 59, 0)
            };

            var result = await service.Insertar(tariff);

            Assert.IsTrue(result.Id > 0);

            await service.Borrar(result.Id);
        }

        // TEST INSERTAR NULL
        [TestMethod]
        public async Task Test_Insertar_Null()
        {
            var service = new ServiceTariff();

            await Assert.ThrowsExceptionAsync<ArgumentNullException>(
                () => service.Insertar(null));
        }

        // TEST ACTUALIZAR EXISTENTE
        [TestMethod]
        public async Task Test_Actualizar_Existente()
        {
            var service = new ServiceTariff();

            var tariff = new Tariff
            {
                Name = "TARIFA_UPDATE",
                PricePerKWh = 0.30m,
                StartHour = new TimeSpan(0, 0, 0),
                EndHour = new TimeSpan(12, 0, 0)
            };

            var inserted = await service.Insertar(tariff);

            inserted.Name = "TARIFA_UPDATE_EDITADA";
            inserted.PricePerKWh = 0.35m;

            var result = await service.Actualizar(inserted);

            Assert.IsTrue(result);

            await service.Borrar(inserted.Id);
        }

        // TEST ACTUALIZAR INEXISTENTE
        [TestMethod]
        public async Task Test_Actualizar_Inexistente()
        {
            var service = new ServiceTariff();

            var fake = new Tariff
            {
                Id = -9999,
                Name = "Fake",
                PricePerKWh = 0.10m,
                StartHour = new TimeSpan(0, 0, 0),
                EndHour = new TimeSpan(1, 0, 0)
            };

            var result = await service.Actualizar(fake);

            Assert.IsFalse(result);
        }

        // TEST BORRAR EXISTENTE
        [TestMethod]
        public async Task Test_Borrar()
        {
            var service = new ServiceTariff();

            var tariff = new Tariff
            {
                Name = "TARIFA_DELETE",
                PricePerKWh = 0.40m,
                StartHour = new TimeSpan(0, 0, 0),
                EndHour = new TimeSpan(23, 0, 0)
            };

            var inserted = await service.Insertar(tariff);

            var result = await service.Borrar(inserted.Id);

            Assert.IsTrue(result);
        }

        // TEST BORRAR INEXISTENTE
        [TestMethod]
        public async Task Test_Borrar_Inexistente()
        {
            var service = new ServiceTariff();

            var result = await service.Borrar(-9999);

            Assert.IsFalse(result);
        }
    }
}
