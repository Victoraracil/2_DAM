using Microsoft.EntityFrameworkCore;
using System.Collections.ObjectModel;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace Video_Club
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private List<Pelicula> todasPeliculas = new();                          // Lista de peliculas
        private List<Cliente> todosClientes = new();                            // Lista de clientes
        private List<Categoria> todasCategorias = new();                        // Lista de categorias
        private ObservableCollection<Pelicula> peliculasVisibles = new();       // Lo que mostramos en la UI
        private ObservableCollection<Cliente> clientesVisibles = new();         // Lo que mostramos en la UI
        private ObservableCollection<Categoria> categoriasVisibles = new();     // Lo que mostramos en la UI

        public MainWindow()
        {
            InitializeComponent();
            // Asignamos los ItemSource a cada elemento
            cmbClientes.ItemsSource = clientesVisibles;
            lstPeliculas.ItemsSource = peliculasVisibles;
            cmbCategorias.ItemsSource = categoriasVisibles;

            // Cargamos los datos en la interfaz
            _ = CargarFiltroClientes();
            _ = CargarFiltroCategorias();
            _ = CargarPeliculas();
        }

        private async Task CargarFiltroClientes()
        {
            // Conectamos con la base de datos
            ServiceCliente service = new ServiceCliente();
            todosClientes = await service.Listar();

            // Cargamos los clientes en la observable collection
            clientesVisibles.Clear();
            foreach (var c in todosClientes)
            {
                clientesVisibles.Add(c);
            }

        }

        private async Task CargarFiltroCategorias()
        {
            // Conectamos con la base de datos
            ServiceCategoria service = new ServiceCategoria();
            todasCategorias = await service.Listar();

            // Cargamos las categorias en la observable collection
            categoriasVisibles.Clear();
            foreach (var cat in todasCategorias)
            {
                categoriasVisibles.Add(cat);
            }

        }


        private async Task CargarPeliculas()
        {
            // Conectamos con la base de datos
            ServicePelicula service = new ServicePelicula();
            todasPeliculas = await service.Listar();

            // Cargamos las peliculas en la observable collection
            peliculasVisibles.Clear();
            foreach (var p in todasPeliculas)
            {
                peliculasVisibles.Add(p);
            }
            AplicarFiltros();
        }

        private void Filtros_Changed(object sender, RoutedEventArgs e)
        {
            AplicarFiltros();

        }

        private void AplicarFiltros()
        {
            // Extraemos la informacion de los filtros
            var filtroCliente = cmbClientes.SelectedItem as Cliente;
            string filtroTitulo = txtBuscarTitulo.Text.Trim().ToLower();
            var filtroCategoria = cmbCategorias.SelectedItem as Categoria;

            // Filtramos la coleccion con los datos puestos en el buscador
            var filtradas = todasPeliculas.Where(p =>
                (filtroCliente == null || p.ClienteId == filtroCliente.Id) &&
                (string.IsNullOrEmpty(filtroTitulo) || p.Titulo.ToLower().Contains(filtroTitulo)) &&
                (filtroCategoria == null || p.CategoriaId == filtroCategoria.Id));

            // Recargamos la ObservableCollection con los datos que queremos mostrar
            peliculasVisibles.Clear();
            foreach (var p in filtradas)
            {
                peliculasVisibles.Add(p);
            }
        }

        private async void btnAgregar_Click(object sender, RoutedEventArgs e)
        {
            var ventanaCrear = new CrearPeliculaWindow();
            bool? resultado = ventanaCrear.ShowDialog();

            if (resultado == true)
            {
                await CargarPeliculas();
                AplicarFiltros(); // Reaplica los filtros activos
            }
        }

        private async void lstPeliculas_MouseDoubleClick(object sender, System.Windows.Input.MouseButtonEventArgs e)
        {
            if (lstPeliculas.SelectedItem is Pelicula p)
            {
                var ventanaEditar = new EditarPeliculaWindow(p.Id);
                bool? resultado = ventanaEditar.ShowDialog();

                if (resultado == true)
                {
                    await CargarPeliculas();
                    AplicarFiltros();
                }
            }
        }

        private void Reset_Click(object sender, RoutedEventArgs e)
        {
            cmbCategorias.SelectedIndex = -1;
            AplicarFiltros();
        }
    }
}