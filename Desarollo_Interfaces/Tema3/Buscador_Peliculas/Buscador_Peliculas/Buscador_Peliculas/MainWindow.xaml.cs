using System.Collections.ObjectModel;
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

namespace Buscador_Peliculas
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private List<Pelicula> todasPeliculas = new(); // Lista completa no observable
        private ObservableCollection<Pelicula> peliculasVisibles = new();  // Lo que se muestra en UI

        public MainWindow()
        {
            InitializeComponent();

            // Asignamos la coleccion observable  una sola vez
            //lvPeliculas.ItemsSource = peliculasVisibles;
            //lbPeliculas.ItemsSource = peliculasVisibles;
            this.DataContext = peliculasVisibles;

            // Caragamos las peliculas desde el origen de datos
            _ = CargarPeliculas();
        }

        private async Task CargarPeliculas()
        {
            // Nos conectamos a la BBDD
            ServicePelicula service = new ServicePelicula();

            // Nos traemos todas las peliculas de la BBDD
            todasPeliculas = await service.Listar();

            // Cargamos las peliculas en la observable collection
            peliculasVisibles.Clear();
            foreach (var p in todasPeliculas)
            {
                peliculasVisibles.Add(p);
            }
        }

        private void Filtro_TextChanged(object sender, TextChangedEventArgs e)
        {
            // Extraemos la informacion de los filtros
            string filtroTitulo = txtTitulo.Text.Trim().ToLower();
            string filtroDirector = txtDirector.Text.Trim().ToLower();
            string filtroYear = txtYear.Text.Trim();

            // Filtramos la coleccion con los datos puestos en el buscador
            var filtradas = todasPeliculas.Where(p =>
                (string.IsNullOrEmpty(filtroTitulo) || p.Titulo.ToLower().Contains(filtroTitulo)) &&
                (string.IsNullOrEmpty(filtroDirector) || p.Director.ToLower().Contains(filtroDirector)) &&
                (string.IsNullOrEmpty(filtroYear) || p.Year.ToString().Contains(filtroYear))
            );

            // Recargamos la ObservableCollection con los datos que queremos mostrar
            peliculasVisibles.Clear();
            foreach (var p in filtradas)
            {
                peliculasVisibles.Add(p);
            }
        }
    }
}