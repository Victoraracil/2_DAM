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
    public class ServiceChargingSessionTests
    {
        // TEST LISTAR
        [TestMethod]
        public async Task Test_Listar()
        {
            var service = new ServiceChargingSession();

            var lista = await service.Listar();

            Assert.IsNotNull(lista);
        }

        // TEST INSERTAR
        [TestMethod]
        public async Task Test_Insertar()
        {
            var sessionService = new ServiceChargingSession();
            var chargerService = new ServiceCharger();

            // Creamos estación
            var station = new Station
            {
                Name = "STATION_TEST",
                Address = "Address Test",
                Latitude = 0,
                Longitude = 0,
                IsActive = true
            };

            using (var context = new ChargingDbContext())
            {
                context.Stations.Add(station);
                await context.SaveChangesAsync();
            }

            // Creamos cargador
            var charger = new Charger
            {
                StationId = station.Id,
                Type = 1,
                MaxPower = 50,
                IsOccupied = false
            };

            var insertedCharger = await chargerService.Insertar(charger);

            // Creamos usuario
            var user = new User
            {
                FullName = "USER_TEST",
                Email = "test@test.com",
                RFIDTag = "TAG_" + new Random().Next(1000, 9999),
                Balance = 100
            };

            using (var context = new ChargingDbContext())
            {
                context.Users.Add(user);
                await context.SaveChangesAsync();
            }

            // Creamos sesión
            var session = new ChargingSession
            {
                ChargerId = insertedCharger.Id,
                UserId = user.Id,
                StartTime = TimeSpan.FromHours(1),
                EndTime = TimeSpan.FromHours(2),
                KWhConsumed = 10,
                TotalCost = 5
            };

            var result = await sessionService.Insertar(session);

            Assert.IsTrue(result.Id > 0);

            // Limpieza
            await sessionService.Borrar(result.Id);
            await chargerService.Borrar(insertedCharger.Id);

            using (var context = new ChargingDbContext())
            {
                context.Users.Remove(context.Users.First(u => u.Id == user.Id));
                context.Stations.Remove(context.Stations.First(s => s.Id == station.Id));
                await context.SaveChangesAsync();
            }
        }

        // TEST INSERTAR NULL
        [TestMethod]
        public async Task Test_Insertar_Null()
        {
            var service = new ServiceChargingSession();

            await Assert.ThrowsExceptionAsync<ArgumentNullException>(
                () => service.Insertar(null));
        }

        // TEST ACTUALIZAR EXISTENTE
        [TestMethod]
        public async Task Test_Actualizar_Existente()
        {
            var sessionService = new ServiceChargingSession();

            using (var context = new ChargingDbContext())
            {
                var station = new Station
                {
                    Name = "STATION_TEST2",
                    Address = "Address Test",
                    Latitude = 0,
                    Longitude = 0
                };

                context.Stations.Add(station);
                await context.SaveChangesAsync();

                var charger = new Charger
                {
                    StationId = station.Id,
                    Type = 1,
                    MaxPower = 50
                };

                context.Chargers.Add(charger);

                var user = new User
                {
                    FullName = "USER_TEST2",
                    Email = "test2@test.com",
                    RFIDTag = "TAG_" + new Random().Next(1000, 9999),
                    Balance = 100
                };

                context.Users.Add(user);
                await context.SaveChangesAsync();

                var session = new ChargingSession
                {
                    ChargerId = charger.Id,
                    UserId = user.Id,
                    StartTime = TimeSpan.FromHours(1),
                    KWhConsumed = 5,
                    TotalCost = 2
                };

                context.ChargingSessions.Add(session);
                await context.SaveChangesAsync();

                session.TotalCost = 10;

                var result = await sessionService.Actualizar(session);

                Assert.IsTrue(result);

                context.ChargingSessions.Remove(session);
                context.Users.Remove(user);
                context.Chargers.Remove(charger);
                context.Stations.Remove(station);
                await context.SaveChangesAsync();
            }
        }

        // TEST ACTUALIZAR INEXISTENTE
        [TestMethod]
        public async Task Test_Actualizar_Inexistente()
        {
            var service = new ServiceChargingSession();

            var fake = new ChargingSession
            {
                Id = -9999,
                ChargerId = 1,
                UserId = 1,
                StartTime = TimeSpan.FromHours(1),
                KWhConsumed = 5,
                TotalCost = 2
            };

            var result = await service.Actualizar(fake);

            Assert.IsFalse(result);
        }

        // TEST BORRAR INEXISTENTE
        [TestMethod]
        public async Task Test_Borrar_Inexistente()
        {
            var service = new ServiceChargingSession();

            var result = await service.Borrar(-9999);

            Assert.IsFalse(result);
        }
    }
}
