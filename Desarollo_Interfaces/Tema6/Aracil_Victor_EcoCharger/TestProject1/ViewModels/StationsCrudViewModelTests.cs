using Aracil_Victor_EcoCharger.Models;
using Aracil_Victor_EcoCharger.ViewModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TestProject1.ViewModels
{

    [TestClass]
    public class StationsCrudViewModelTests
    {
        // TEST CARGAR COMMAND
        [TestMethod]
        public async Task Test_CargarCommand()
        {
            var vm = new StationsCrudViewModel();

            vm.CargarCommand.Execute(null);
            await Task.Delay(1500);

            Assert.IsNotNull(vm.ListaEstacionesVisibles);
        }

        // TEST NUEVA ESTACION
        [TestMethod]
        public void Test_NuevaEstacionCommand()
        {
            var vm = new StationsCrudViewModel();

            vm.NuevaEstacionCommand.Execute(null);

            Assert.AreEqual(0, vm.Id);
            Assert.IsTrue(vm.IsActive);
            Assert.AreEqual(1, vm.Chargers.Count);
        }

        // TEST NUEVO CARGADOR
        [TestMethod]
        public void Test_NuevoCargadorCommand()
        {
            var vm = new StationsCrudViewModel();

            vm.NuevaEstacionCommand.Execute(null);
            vm.NuevoCargadorCommand.Execute(null);

            Assert.AreEqual(2, vm.Chargers.Count);
        }

        // TEST BORRAR CARGADOR
        [TestMethod]
        public void Test_BorrarCargadorCommand()
        {
            var vm = new StationsCrudViewModel();

            vm.NuevaEstacionCommand.Execute(null);
            vm.NuevoCargadorCommand.Execute(null);

            var cargador = vm.Chargers.First();

            vm.BorrarCargadorCommand.Execute(cargador);

            Assert.AreEqual(1, vm.Chargers.Count);
        }

        // TEST GUARDAR ESTACION (INSERTAR)
        [TestMethod]
        public async Task Test_GuardarEstacion_Insertar()
        {
            var vm = new StationsCrudViewModel();

            vm.NuevaEstacionCommand.Execute(null);

            vm.Name = "ESTACION_TEST_CRUD";
            vm.Address = "Direccion Test";
            vm.Latitude = 1;
            vm.Longitude = 1;

            vm.GuardarEstacionCommand.Execute(null);
            await Task.Delay(2000);

            Assert.IsTrue(vm.Id > 0);

            // Limpieza
            vm.BorrarEstacionCommand.Execute(null);
            await Task.Delay(1500);
        }

        // TEST GUARDAR ESTACION (ACTUALIZAR)
        [TestMethod]
        public async Task Test_GuardarEstacion_Actualizar()
        {
            var vm = new StationsCrudViewModel();

            vm.NuevaEstacionCommand.Execute(null);

            vm.Name = "ESTACION_EDITAR";
            vm.Address = "Direccion Editar";
            vm.Latitude = 1;
            vm.Longitude = 1;

            vm.GuardarEstacionCommand.Execute(null);
            await Task.Delay(2000);

            int idCreado = vm.Id;

            vm.Name = "ESTACION_EDITADA";
            vm.GuardarEstacionCommand.Execute(null);
            await Task.Delay(2000);

            vm.CargarCommand.Execute(null);
            await Task.Delay(1500);

            var editada = vm.ListaEstacionesVisibles
                .First(e => e.Id == idCreado);

            Assert.AreEqual("ESTACION_EDITADA", editada.Name);

            vm.Selected = editada;
            vm.SelectedItemChangedCommand.Execute(editada);

            vm.BorrarEstacionCommand.Execute(null);
            await Task.Delay(1500);
        }

        // TEST BORRAR ESTACION
        [TestMethod]
        public async Task Test_BorrarEstacionCommand()
        {
            var vm = new StationsCrudViewModel();

            vm.NuevaEstacionCommand.Execute(null);

            vm.Name = "ESTACION_BORRAR";
            vm.Address = "Direccion Borrar";
            vm.Latitude = 1;
            vm.Longitude = 1;

            vm.GuardarEstacionCommand.Execute(null);
            await Task.Delay(2000);

            int idCreado = vm.Id;

            vm.BorrarEstacionCommand.Execute(null);
            await Task.Delay(2000);

            vm.CargarCommand.Execute(null);
            await Task.Delay(1500);

            Assert.IsFalse(vm.ListaEstacionesVisibles.Any(e => e.Id == idCreado));
        }

        // TEST SELECTED ITEM CHANGED
        [TestMethod]
        public void Test_SelectedItemChanged()
        {
            var vm = new StationsCrudViewModel();

            var station = new Station
            {
                Id = 99,
                Name = "Seleccionada",
                Address = "Direccion",
                Latitude = 1,
                Longitude = 1,
                IsActive = true,
                Chargers = new System.Collections.ObjectModel.ObservableCollection<Charger>()
            };

            vm.Selected = station;
            vm.SelectedItemChangedCommand.Execute(station);

            Assert.AreEqual(99, vm.Id);
            Assert.AreEqual("Seleccionada", vm.Name);
        }
    }
}
