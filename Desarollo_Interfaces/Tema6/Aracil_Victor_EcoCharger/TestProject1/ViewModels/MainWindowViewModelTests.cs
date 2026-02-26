using Aracil_Victor_EcoCharger.ViewModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TestProject1.ViewModels
{

    [TestClass]
    public class MainWindowViewModelTests
    {
        // TEST ESTADO INICIAL
        [TestMethod]
        public void Test_Constructor_EstadoInicial()
        {
            var vm = new MainWindowViewModel();

            Assert.IsNotNull(vm.CurrentView);
            Assert.IsInstanceOfType(vm.CurrentView, typeof(PrincipalViewModel));
        }

        // TEST SHOW MAIN COMMAND
        [TestMethod]
        public void Test_ShowMainCommand()
        {
            var vm = new MainWindowViewModel();

            vm.ShowMainCommand.Execute(null);

            Assert.IsInstanceOfType(vm.CurrentView, typeof(PrincipalViewModel));
        }

        // TEST SHOW STATIONS COMMAND
        [TestMethod]
        public void Test_ShowStationsCommand()
        {
            var vm = new MainWindowViewModel();

            vm.ShowStationsCommand.Execute(null);

            Assert.IsInstanceOfType(vm.CurrentView, typeof(StationsViewModel));
        }

        // TEST SHOW STATIONS CRUD COMMAND
        [TestMethod]
        public void Test_ShowStationsCRUDCommand()
        {
            var vm = new MainWindowViewModel();

            vm.ShowStationsCRUDCommand.Execute(null);

            Assert.IsInstanceOfType(vm.CurrentView, typeof(StationsCrudViewModel));
        }
    }
}
