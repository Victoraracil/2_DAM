using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace Video_Club
{
    /// <summary>
    /// Lógica de interacción para CrearPeliculaWindow.xaml
    /// </summary>
    public partial class CrearPeliculaWindow : Window
    {
        private List<Cliente> todosClientes = new();                            // Lista de clientes
        private List<Categoria> todasCategorias = new();                        // Lista de categorias
        public CrearPeliculaWindow()
        {
            InitializeComponent();

            // Cargamos los Combo Box
            _ = CargarClientes();
            _ = CargarCategorias();
        }

        private async Task CargarClientes()
        {
            // Conectamos a la base de datos
            ServiceCliente service = new ServiceCliente();
            todosClientes = await service.Listar();

            // Asignamos los ItemSource a cada elemento
            cmbCliente.ItemsSource = todosClientes;
        }

        private async Task CargarCategorias()
        {
            // Conectamos a la base de datos
            ServiceCategoria service = new ServiceCategoria();
            todasCategorias = await service.Listar();

            // Asignamos los ItemSource a cada elemento
            cmbCategoria.ItemsSource = todasCategorias;
        }
        private async void btnGuardar_Click(object sender, RoutedEventArgs e)
        {
            // Hariamos las comprobaciones de que todo esta cumplimentado adecuadamente
            if (string.IsNullOrWhiteSpace(txtTitulo.Text) || string.IsNullOrWhiteSpace(txtDirector.Text) || string.IsNullOrWhiteSpace(txtYear.Text) || cmbCategoria.SelectedIndex == -1 || cmbCliente.SelectedIndex == -1)
            {
                MessageBox.Show("ERROR: No se puede crear la pelicula. Por favor, rellene todos los campos");
            }
            else
            {
                // Conectamos con la base de datos
                ServicePelicula service = new ServicePelicula();
                Pelicula peli = new Pelicula()
                {
                    Titulo = txtTitulo.Text.Trim(),
                    Director = txtDirector.Text.Trim(),
                    Year = int.Parse(txtYear.Text.Trim()),
                    CategoriaId = cmbCategoria.SelectedIndex + 1,
                    ClienteId = cmbCliente.SelectedIndex + 1
                };

                // Creamos la pelicula
                var creada = await service.Insertar(peli);

                if (creada != null)
                {
                    MessageBox.Show("Pelicula creada correctamente!");
                    this.DialogResult = true;
                    this.Close();
                }
                else
                {
                    MessageBox.Show("Ha ocurrido un error al crear la pelicula.");
                }
            }
        }

        private void btnCancelar_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }
    }
}
