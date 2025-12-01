using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using TaskManager.Data.Models;
using Aracil_Victor_GestionTareas03._01.Migrations;

namespace Aracil_Victor_GestionTareas03._01
{
    /// <summary>
    /// Lógica de interacción para BajaUsuario.xaml
    /// </summary>
    ///<author> Victor Aracil Gozalvez</author>
    public partial class BajaUsuario : Window
    {
        // Colección mostrada en la vista (vinculada desde XAML)
        public ObservableCollection<User> UsuariosVisibles { get; } = new ObservableCollection<User>();

        // Lista completa cargada desde la BD (para filtrar)
        private List<User> _allUsuarios = new List<User>();

        public BajaUsuario()
        {
            InitializeComponent();
            DataContext = this;

            
            Loaded += async (s, e) => await LoadUsuariosAsync();
        }

        private async Task LoadUsuariosAsync()
        {
            try
            {
                using (var svc = new ServiceUser())
                {
                    var list = await svc.Listar();
                    _allUsuarios = list ?? new List<User>();

                    // Rellenar la colección observable
                    UsuariosVisibles.Clear();
                    foreach (var u in _allUsuarios)
                    {
                        // Mostrar solo activos. Cambie la condición si no aplica.
                        if (u is not null && (u.Activo == 1))
                            UsuariosVisibles.Add(u);
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error cargando usuarios: {ex.Message}", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        // Filtrado incremental por nombre
        private void txtNombre_TextChanged(object sender, TextChangedEventArgs e)
        {
            ApplyFilter();
        }

        // Filtrado incremental por email
        private void txtEmail_TextChanged(object sender, TextChangedEventArgs e)
        {
            ApplyFilter();
        }

        private void ApplyFilter()
        {
            var nombre = txtNombre.Text?.Trim().ToLowerInvariant() ?? string.Empty;
            var email = txtEmail.Text?.Trim().ToLowerInvariant() ?? string.Empty;

            var filtrados = _allUsuarios.Where(u =>
                // Solo usuarios activos
                (u.Activo == 1) &&
                // Nombre / usuario
                (string.IsNullOrEmpty(nombre)
                    || (u.NombreCompleto?.ToLowerInvariant().Contains(nombre) ?? false)
                    || (u.Usuario?.ToLowerInvariant().Contains(nombre) ?? false))
                &&
                // Email
                (string.IsNullOrEmpty(email)
                    || (u.CorreoElectronico?.ToLowerInvariant().Contains(email) ?? false))
            ).ToList();

            UsuariosVisibles.Clear();
            foreach (var u in filtrados)
                UsuariosVisibles.Add(u);
        }

        private async void btnDarBaja_Click(object sender, RoutedEventArgs e)
        {
            var seleccionado = lbUsuarios.SelectedItem as User;
            if (seleccionado == null)
            {
                MessageBox.Show("Seleccione un usuario para dar de baja.", "Atención", MessageBoxButton.OK, MessageBoxImage.Information);
                return;
            }

            var confirmar = MessageBox.Show("¿Confirma dar de baja al usuario seleccionado?", "Confirmar baja", MessageBoxButton.YesNo, MessageBoxImage.Question);
            if (confirmar == MessageBoxResult.Yes)
            {
                // TODO: llamar al servicio/repositorio para realizar la baja real y refrescar la lista.
                ServiceUser service = new ServiceUser();
                bool dadoBaja = await service.Borrar((int)seleccionado.Id);
                if (dadoBaja) 
                {
                    MessageBox.Show("Operación de baja realizada.", "Información", MessageBoxButton.OK, MessageBoxImage.Information);
                    await LoadUsuariosAsync();
                }
                else
                {
                    MessageBox.Show($"Error borrando usuarios");
                }
                
            }
        }

        // Manejador para el botón "Cerrar"
        private void Close_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }
    }
}
