using Aracil_Victor_EcoCharger.ViewModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TestProject1.ViewModels
{

    [TestClass]
    public class PrincipalViewModelTests
    {
        // TEST CONSTRUCTOR
        [TestMethod]
        public void Test_Constructor()
        {
            var vm = new PrincipalViewModel();

            Assert.IsNotNull(vm);
        }
    }

}
