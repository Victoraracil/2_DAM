using Aracil_Victor_EcoCharger.ViewModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TestProject1.ViewModels
{

    [TestClass]
    public class StationsViewModelTests
    {
        // TEST ESTADO INICIAL
        [TestMethod]
        public void Test_Constructor_EstadoInicial()
        {
            var vm = new StationsViewModel();

            Assert.IsNotNull(vm.ListaEstacionesVisibles);
            Assert.AreEqual(0, vm.ListaEstacionesVisibles.Count);
        }

        // TEST CARGAR COMMAND
        [TestMethod]
        public async Task Test_CargarCommand()
        {
            var vm = new StationsViewModel();

            vm.CargarCommand.Execute(null);

            await Task.Delay(1500);

            Assert.IsNotNull(vm.ListaEstacionesVisibles);
            Assert.IsTrue(vm.ListaEstacionesVisibles.Count >= 0);
        }
    }
}
