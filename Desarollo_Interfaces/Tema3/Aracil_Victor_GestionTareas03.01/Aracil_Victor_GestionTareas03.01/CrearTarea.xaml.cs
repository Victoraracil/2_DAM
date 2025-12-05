using Aracil_Victor_GestionTareas03._01.Migrations;
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

namespace Aracil_Victor_GestionTareas03._01
{
    /// <summary>
    /// Lógica de interacción para CrearTarea.xaml
    /// </summary>
    public partial class CrearTarea : Window
    {
        public CrearTarea(int userIdActual)
        {
            InitializeComponent();
            CargarUsuarios();
            CargarEtiquetas();
        }

        private async void CargarUsuarios()
        {
            try
            {
                using var service = new ServiceUser();
                var usuarios = await service.Listar();

                cmbUsuario.ItemsSource = usuarios;
                cmbUsuario.DisplayMemberPath = "NombreCompleto"; // Nombre a mostrar
                cmbUsuario.SelectedValuePath = "Id";              // FK real
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error cargando usuarios: " + ex.Message);
            }
        }

        private async void CargarEtiquetas()
        {
            try
            {
                using var service = new ServiceEtiqueta();
                var etiquetas = await service.Listar();

                cmbEtiqueta.ItemsSource = etiquetas;
                cmbEtiqueta.DisplayMemberPath = "Nombre"; // campo real de Etiqueta
                cmbEtiqueta.SelectedValuePath = "Id";     // FK real
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error cargando etiquetas: " + ex.Message);
            }
        }


        private async void Crear_Click(object sender, RoutedEventArgs e)
        {
            bool valido = true;

            // Validación de título
            if (string.IsNullOrWhiteSpace(txtTitulo.Text))
            {
                Marcar(txtTitulo, false);
                valido = false;
            }
            else
                Marcar(txtTitulo, true);

            // Validación de descripción
            if (string.IsNullOrWhiteSpace(txtDescripcion.Text))
            {
                Marcar(txtDescripcion, false);
                valido = false;
            }
            else
                Marcar(txtDescripcion, true);

            // Validación estado
            if (cmbEstado.SelectedIndex < 0)
            {
                Marcar(cmbEstado, false);
                valido = false;
            }
            else
                Marcar(cmbEstado, true);

            // Validación color
            if (cmbColor.SelectedIndex < 0)
            {
                Marcar(cmbColor, false);
                valido = false;
            }
            else
                Marcar(cmbColor, true);

            // Validación vencimiento
            if (dpVencimiento.SelectedDate == null)
            {
                Marcar(dpVencimiento, false);
                valido = false;
            }
            else
                Marcar(dpVencimiento, true);

            // Validación usuario
            if (cmbUsuario.SelectedItem == null)
            {
                Marcar(cmbUsuario, false);
                valido = false;
            }
            else
                Marcar(cmbUsuario, true);

            // Validación etiqueta
            if (cmbEtiqueta.SelectedItem == null)
            {
                Marcar(cmbEtiqueta, false);
                valido = false;
            }
            else
                Marcar(cmbEtiqueta, true);

            // Si algo está mal, no seguimos
            if (!valido)
            {
                MessageBox.Show("Hay campos inválidos o vacíos. Revise el formulario.");
                return;
            }

            var nuevaTarea = new Tarea
            {
                Titulo = txtTitulo.Text,
                Descripcion = txtDescripcion.Text,
                Estado = (EstadoTarea)cmbEstado.SelectedIndex,
                Vencimiento = dpVencimiento.SelectedDate.Value,
                Color = cmbColor.SelectedIndex,              // 0–4
                UserId = (int)cmbUsuario.SelectedValue,     // FK usuario
                EtiquetaId = (int)cmbEtiqueta.SelectedValue // FK etiqueta
            };

            var service = new ServiceTarea();
            var tareaCreada = await service.Insertar(nuevaTarea);

            if (tareaCreada != null)
                MessageBox.Show("Tarea creada correctamente.");
            else
                MessageBox.Show("Ha ocurrido un error creando la tarea.");

            LimpiarInterfaz();
        }

        private void LimpiarInterfaz()
        {
            txtTitulo.Text = "";
            txtDescripcion.Text = "";
            cmbEstado.SelectedIndex = -1;
            dpVencimiento.SelectedDate = null;
            cmbColor.SelectedIndex = -1;
            cmbUsuario.SelectedIndex = -1;
            cmbEtiqueta.SelectedIndex = -1;
        }

        private void Marcar(Control control, bool esValido)
        {
            if (esValido)
                control.Style = (Style)FindResource("OkStyle");
            else
                control.Style = (Style)FindResource("ErrorStyle");
        }


        private void txtNuevoFullName_TextChanged(object sender, TextChangedEventArgs e)
        {

        }

        private void Filtro_TextChanged(object sender, SelectionChangedEventArgs e)
        {

        }
    }
}
