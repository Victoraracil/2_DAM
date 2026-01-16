using Aracil_Victor_EcoCharger.ViewModels;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace Aracil_Victor_EcoCharger
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    /// <author> Victor Aracil Gozalvez</author>

    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
            DataContext = new MainWindowViewModel();
        }

        
    }
}