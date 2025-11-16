using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
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
    /// Lógica de interacción para EditarPeliculaWindow.xaml
    /// </summary>
    public partial class EditarPeliculaWindow : Window
    {
        public EditarPeliculaWindow(int peliculaId)
        {
            InitializeComponent();

            // Cargamos los datos de la pelicula si existe
            _ = CargarPelicula(peliculaId);

            // Cargamos los Combo Box
            _ = CargarClientes();
            _ = CargarCategorias();

        }

        private async Task CargarPelicula(int p_Id)
        {
            // Conectamos con la base de datos
            ServicePelicula service = new ServicePelicula();
            var peli = await service.Listar(p_Id);

            // Cargamos las peliculas en la interfaz 
            if(peli != null)
            {
                txtId.Text = peli.Id.ToString();
                txtTitulo.Text = peli.Titulo;
                txtDirector.Text = peli.Director;
                txtYear.Text = peli.Year.ToString();
                cmbCategoria.SelectedValue = peli.CategoriaId;
                cmbCliente.SelectedValue = peli.ClienteId; // si null -> 0 o nada
            }
            else
            {
                MessageBox.Show("Error al cargar los datos de la pelicula");
                this.Close();
            }
        }

        private async Task CargarClientes()
        {
            // Iniciamos la lista de clientes
            List<Cliente> clientes = new List<Cliente>();

            // Conectamos a la base de datos
            ServiceCliente service = new ServiceCliente();
            clientes = await service.Listar();

            // Añadimos los clientes a la interfaz
            cmbCliente.ItemsSource = clientes;
        }

        private async Task CargarCategorias()
        {
            // Iniciamos la lista de clientes
            List<Categoria> categorias = new List<Categoria>();

            // Conectamos a la base de datos
            ServiceCategoria service = new ServiceCategoria();
            categorias = await service.Listar();

            // Añadimos los clientes a la interfaz
            cmbCategoria.ItemsSource = categorias;

        }

        private async void btnGuardar_Click(object sender, RoutedEventArgs e)
        {
            // Hariamos las comprobaciones de que todo esta cumplimentado adecuadamente
            if (string.IsNullOrWhiteSpace(txtTitulo.Text) || string.IsNullOrWhiteSpace(txtDirector.Text) || string.IsNullOrWhiteSpace(txtYear.Text) || cmbCategoria.SelectedIndex == -1 || cmbCliente.SelectedIndex == -1)
            {
                MessageBox.Show("ERROR: No se puede actualizar la pelicula. Por favor, rellene todos los campos");
            }
            else
            {
                // Conectamos con la base de datos
                ServicePelicula service = new ServicePelicula();
                var peli = await service.Listar(int.Parse(txtId.Text));
                if (peli != null)
                {
                    peli.Titulo = txtTitulo.Text.Trim();
                    peli.Director = txtDirector.Text.Trim();
                    peli.Year = int.Parse(txtYear.Text);
                    peli.CategoriaId = cmbCategoria.SelectedIndex + 1;
                    peli.ClienteId = cmbCliente.SelectedIndex + 1;

                    // Actualizamos la pelicula
                    var actualizada = await service.Actualizar(peli);
                    if (actualizada)
                    {
                        MessageBox.Show("Pelicula actualizada correctamente.");
                        this.DialogResult = true;
                        this.Close();
                    }
                    else
                    {
                        MessageBox.Show("Ha ocurrido un error en el proceso de actualización.");
                    }
                }
                else
                {
                    MessageBox.Show("Ha ocurrido un error.");
                    this.Close();
                }
            }
        }

        private void btnCancelar_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }
    }
}
