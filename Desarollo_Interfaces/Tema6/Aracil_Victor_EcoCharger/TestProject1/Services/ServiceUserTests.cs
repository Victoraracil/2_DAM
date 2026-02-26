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
    public class ServiceUserTests
    {
        // TEST LISTAR
        [TestMethod]
        public async Task Test_Listar()
        {
            var service = new ServiceUser();

            var lista = await service.Listar();

            Assert.IsNotNull(lista);
        }

        // TEST INSERTAR
        [TestMethod]
        public async Task Test_Insertar()
        {
            var service = new ServiceUser();

            var user = new User
            {
                FullName = "Usuario Test",
                Email = "test@test.com",
                RFIDTag = "RFID_" + Guid.NewGuid().ToString("N").Substring(0, 10),
                Balance = 50.00m
            };

            var result = await service.Insertar(user);

            Assert.IsTrue(result.Id > 0);

            await service.Borrar(result.Id);
        }

        // TEST INSERTAR NULL
        [TestMethod]
        public async Task Test_Insertar_Null()
        {
            var service = new ServiceUser();

            await Assert.ThrowsExceptionAsync<ArgumentNullException>(
                () => service.Insertar(null));
        }

        // TEST ACTUALIZAR EXISTENTE
        [TestMethod]
        public async Task Test_Actualizar_Existente()
        {
            var service = new ServiceUser();

            var user = new User
            {
                FullName = "Usuario Update",
                Email = "update@test.com",
                RFIDTag = "RFID_" + Guid.NewGuid().ToString("N").Substring(0, 10),
                Balance = 20.00m
            };

            var inserted = await service.Insertar(user);

            inserted.FullName = "Usuario Update Editado";
            inserted.Balance = 100.00m;

            var result = await service.Actualizar(inserted);

            Assert.IsTrue(result);

            await service.Borrar(inserted.Id);
        }

        // TEST ACTUALIZAR INEXISTENTE
        [TestMethod]
        public async Task Test_Actualizar_Inexistente()
        {
            var service = new ServiceUser();

            var fake = new User
            {
                Id = -9999,
                FullName = "Fake",
                Email = "fake@test.com",
                RFIDTag = "RFID_FAKE_1",
                Balance = 0
            };

            var result = await service.Actualizar(fake);

            Assert.IsFalse(result);
        }

        // TEST BORRAR EXISTENTE
        [TestMethod]
        public async Task Test_Borrar()
        {
            var service = new ServiceUser();

            var user = new User
            {
                FullName = "Usuario Delete",
                Email = "delete@test.com",
                RFIDTag = "RFID_" + Guid.NewGuid().ToString("N").Substring(0, 10),
                Balance = 10.00m
            };

            var inserted = await service.Insertar(user);

            var result = await service.Borrar(inserted.Id);

            Assert.IsTrue(result);
        }

        // TEST BORRAR INEXISTENTE
        [TestMethod]
        public async Task Test_Borrar_Inexistente()
        {
            var service = new ServiceUser();

            var result = await service.Borrar(-9999);

            Assert.IsFalse(result);
        }
    }
}
